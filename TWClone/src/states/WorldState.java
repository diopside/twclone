package states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Coordinates;
import entities.Draggable;
import entities.world.Territory;
import entities.world.Tile;
import entities.world.World;
import gui.Hud;
import gui.Menu;
import gui.ToolTip;

public class WorldState extends BasicGameState {

	/*
	 * This state will contain the "world" that is each territory and unit placed on those territories
	 */

	private final int ID;
	private static final float SCROLL_SPEED = 1.0f;

	private World world;
	private int xOffset, yOffset;
	private Hud hud;
	private Menu popupMenu;
	private ToolTip toolTip;

	// The following variables will be used in dragging objects around the world
	private ArrayList<Draggable> draggables;
	private Draggable draggedObject;
	private Coordinates draggedObjectOffCoords;
	private ArrayList<Integer> FPS;


	//**************************** Constructors and Initialization Methods ***********************************************
	public WorldState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		world = World.generateWorld();
		hud = new Hud(world);
		hud.getMiniMap().setInformation(world.getMap().getTiles(), world.getMap().getSize());
		popupMenu = new Menu("res/menus/popupmenu.png", 129, 2, 29, 127, 32, world); // the last 5 are the image specific locations of the close box and top bar
		toolTip = new ToolTip(0, 0, new ArrayList<String>());
		draggables = new ArrayList<>();
		draggables.add(popupMenu);
		draggables.add(toolTip);
		
		FPS = new ArrayList<>();
	}


	//******************************* StateBasedGame Methods ************************************************
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		g.setBackground(Color.blue);
		world.render(g, xOffset, yOffset);
		if (popupMenu.isActive())
			popupMenu.render(g, xOffset, yOffset);

		hud.render(g);
		hud.getMiniMap().render(g, xOffset, yOffset);

		if (toolTip.isActive())
			toolTip.render(g, xOffset, yOffset);
		
	} // END RENDER METHOD

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		Input input = container.getInput();
		int mouseX = input.getMouseX(); int mouseY = input.getMouseY();


		/*
		 * This block of code is for map scrolling which can be done with WASD or moving the mouse to the margins of the screen
		 * The nested if statement for each represents the distance off the map that can be scrolled to.  The bottom margin for this
		 * is the greatest so that you can scroll far enough down that the hud doesn't obscure the map
		 */
		if (input.isKeyDown(input.KEY_A) || mouseX <= 20){
			if (xOffset > -100)
				xOffset -= SCROLL_SPEED * delta;
		}
		if (input.isKeyDown(input.KEY_D) || mouseX >= Game.WIDTH - 20){
			if (xOffset + Game.WIDTH < world.getMap().getSize()*Tile.SIZE + 100)
				xOffset += SCROLL_SPEED * delta;
		}
		if (input.isKeyDown(input.KEY_W) || mouseY <= 20){
			if (yOffset > -100)
				yOffset -= SCROLL_SPEED * delta;
		}
		if (input.isKeyDown(input.KEY_S) || mouseY >= Game.HEIGHT - 20){
			if (yOffset + Game.HEIGHT < world.getMap().getSize()*Tile.SIZE + 300) 
				yOffset += SCROLL_SPEED * delta;
		}


		//****************************************
		if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
			if (hud.getMiniMap().getRectangle().contains(mouseX, mouseY)){
				hud.getMiniMap().click(mouseX, mouseY, this);
			}
			for (Territory t: world.getMap().getTerritories()){

				// This block will determine if the player has clicked one of the territory bases
				if (t.onScreen(xOffset, yOffset))
					if (t.onBaseIcon(mouseX, mouseY, xOffset, yOffset)){
						if (popupMenu.isActive())
							popupMenu.deselect();
						popupMenu.selectTerritory(t);
						break;
					}
			} // End territory for loop
			if (popupMenu.isActive()){
				if (popupMenu.getCloseRectangle(xOffset, yOffset).contains(mouseX, mouseY))
					popupMenu.deselect();
				else {
					// This will test to see if any menu button was selected, currently the only one is the button to enter the territory screen.
					popupMenu.checkButtons(mouseX, mouseY, xOffset, yOffset, game);
				}
			}// end popup menu block
		}  // end left button block

		if (input.isMousePressed(input.MOUSE_RIGHT_BUTTON)){
			Tile t = determineMouseTileLocation(mouseX, mouseY);
			if (t != null)
				t.setToolTip(toolTip);

		} // end right click block


		if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON)){
			for (Draggable obj: draggables){
				if (obj.getOffsetShape(xOffset, yOffset).contains(mouseX, mouseY)){
					if (draggedObject == null){
						draggedObject = obj;
						Coordinates objCoord = obj.getCoordinates();
						draggedObjectOffCoords = new Coordinates(mouseX - objCoord.getX(), mouseY - objCoord.getY());
						break;
					}
					else{
						draggedObject.drag(draggedObjectOffCoords, mouseX, mouseY);
					}
				}	
			}
		}
		else
			if (draggedObject != null){
				draggedObject.setDragging(false);
				draggedObject = null;
				
			}
		
		if (input.isKeyPressed(input.KEY_P)){
			System.out.println("Territories - " + world.getMap().getTerritories().length);
			System.out.println("Regions - " + world.getMap().getRegions().size());
			System.out.println("Factions - " + world.getFactions().length);
			long totalFPS = 0;
			for (Integer i: FPS)
				totalFPS += i;
			totalFPS /= FPS.size();
			System.out.println("FPS: "+totalFPS);
		}
		
		if (input.isKeyPressed(input.KEY_O)){
			System.out.println(xOffset + "    -    " + yOffset);
		}
		
		FPS.add(container.getFPS());

	} // END UPDATE METHOD



	@Override
	public int getID() {
		return ID;
	}

	// ******************************************* General Methods ************************************************************
	public Tile determineMouseTileLocation(int mouseX, int mouseY){
		/*
		 * This method will determine which tile the mouse is over
		 * If the mouse is in a location without a tile, the method wil return null
		 */
		int absoluteMouseX = mouseX + xOffset;
		int absoluteMouseY = mouseY + yOffset;
		int tX = absoluteMouseX / Tile.SIZE;
		int tY = absoluteMouseY / Tile.SIZE;

		// world.getMap().size() call determines the bounds of tile locations, the subtraction of 1 is due to array indices
		if (tX >= 0 && tX <= world.getMap().getSize() - 1)
			if (tY >= 0 && tY <= world.getMap().getSize() - 1)
				return world.getMap().getTiles()[tX][tY];
		return null;
	}

	public void setOffsets(int xOff, int yOff){
		xOffset = xOff;
		yOffset = yOff;
	}
	
	public World getWorld(){
		return this.world;
	}


}

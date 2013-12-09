package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.world.Territory;
import entities.world.Tile;
import entities.world.World;
import gui.Hud;
import gui.Menu;

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

	public WorldState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		world = World.generateWorld();
		hud = new Hud();
		hud.getMiniMap().setInformation(world.getMap().getTiles(), world.getMap().getSize());
		popupMenu = new Menu("res/menus/popupmenu.png", 129, 2, 29); // the last 3 are the image specific locations of the close box
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		g.setBackground(Color.black);
		world.render(g, xOffset, yOffset);
		
		
		if (popupMenu.isActive())
			popupMenu.render(g, xOffset, yOffset);
		
		hud.render(g);
		hud.getMiniMap().render(g, xOffset, yOffset);
	}

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
			
			for (Territory t: world.getTerritories()){
				
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
					popupMenu.setActive(false);
				else {
					// This will test to see if any menu button was selected, currently the only one is the button to enter the territory screen.
					popupMenu.checkButtons(mouseX, mouseY, xOffset, yOffset, game);
				}
			}// end popup menu block
		}


	}
	
	public void setOffsets(int xOff, int yOff){
		xOffset = xOff;
		yOffset = yOff;
	}

	@Override
	public int getID() {
		return ID;
	}

}

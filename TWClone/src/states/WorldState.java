package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.world.Territory;
import entities.world.World;
import gui.Hud;
import gui.Menu;

public class WorldState extends BasicGameState {

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
		hud.getMiniMap().setTerritories(world.getTerritories());
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

		if (input.isKeyDown(input.KEY_A) || mouseX <= 20){
			if (xOffset > -100)
				xOffset -= SCROLL_SPEED * delta;
		}
		if (input.isKeyDown(input.KEY_D) || mouseX >= Game.WIDTH - 20){
			if (xOffset + Game.WIDTH < Territory.TERRITORY_SIZE * 8 + 100)
				xOffset += SCROLL_SPEED * delta;
		}
		if (input.isKeyDown(input.KEY_W) || mouseY <= 20){
			if (yOffset > -100)
				yOffset -= SCROLL_SPEED * delta;
		}
		if (input.isKeyDown(input.KEY_S) || mouseY >= Game.HEIGHT - 20){
			if (yOffset + Game.HEIGHT < Territory.TERRITORY_SIZE * 8 + 300)
				yOffset += SCROLL_SPEED * delta;
		}
		
		if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
			for (Territory t: world.getTerritories()){
				if (t.onScreen(xOffset, yOffset))
					if (t.onBaseIcon(mouseX, mouseY, xOffset, yOffset)){
						if (popupMenu.isActive())
							popupMenu.deselect();
						popupMenu.selectTerritory(t);
						break;
					}
						
				
			}
			if (popupMenu.isActive()){
				if (popupMenu.getCloseRectangle(xOffset, yOffset).contains(mouseX, mouseY))
					popupMenu.setActive(false);
			}
		}


	}

	@Override
	public int getID() {
		return ID;
	}

}

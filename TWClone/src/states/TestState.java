package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.world.MapShape;
import entities.world.Region;
import entities.world.Territory;

public class TestState extends BasicGameState{
	

	private static final float SCROLL_SPEED = 1.0f;
	
	private final int ID;
	private MapShape map;
	private int xOffset, yOffset;
	
	public TestState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		map = new MapShape(16);
		long total = 0;
		for (Region r: map.getRegions())
			total += r.getTiles().size();
		System.out.println(total);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		
		
		for (Region r: map.getRegions()){
			
			r.render(g, xOffset, yOffset);
		}

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
		

	}

	@Override
	public int getID() {
		return ID;
	}

}

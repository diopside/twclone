package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.world.MapShape;
import entities.world.Region;
import entities.world.Territory;
import entities.world.Tile;

public class TestState extends BasicGameState{
	

	private static final float SCROLL_SPEED = 1.0f;
	
	private final int ID;
	
	public TestState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		int mouseX = input.getMouseX(); int mouseY = input.getMouseY();

		
		

	}

	@Override
	public int getID() {
		return ID;
	}

}

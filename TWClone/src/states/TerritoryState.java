package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.world.Territory;

public class TerritoryState extends BasicGameState {


	private final int ID;
	private Territory territory;

	public TerritoryState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString(territory.getName(), 100, 100);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if (input.isKeyPressed(input.KEY_ESCAPE))
			game.enterState(Game.WORLD_STATE_ID);
		

	}

	@Override
	public int getID() {
		return ID;
	}
	
	
	
	public void setTerritory(Territory t){
		territory = t;
	}

}

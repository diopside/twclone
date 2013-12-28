package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.Faction;
import entities.world.Territory;

public class EndTurnState extends BasicGameState{

	private final int ID;
	// this boolean will be used for the turn to process, once it is it is made true, when leaving it is made false once again
	private boolean turnProcessed; 


	public EndTurnState(int id){
		ID = id;
		turnProcessed = false;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		g.setBackground(Color.black);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();

		if (turnProcessed){

			if (input.isKeyPressed(input.KEY_ENTER)){
				turnProcessed = false;
				game.enterState(Game.WORLD_STATE_ID);
			}
		}
		
		else{
			processTurn(game);
			turnProcessed = true;
			
		}
	}
	
	private void processTurn(StateBasedGame game){
		WorldState worldState = ((WorldState) game.getState(Game.WORLD_STATE_ID));
		worldState.incYear();
		
		for (Faction f: worldState.getWorld().getFactions()){
			f.processTurn();
		}
		
		for (Territory t: worldState.getWorld().getMap().getTerritories()){
			t.processTurn();
		}
		
	}

	@Override
	public int getID() {
		return ID;
	}


}






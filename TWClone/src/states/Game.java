package states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

	public static final int WIDTH = 1200, HEIGHT = 800;
	public static final Rectangle SCREEN = new Rectangle(0, 0, WIDTH, HEIGHT);
	//State IDs
	public static final int MAIN_MENU_STATE_ID = 0, WORLD_STATE_ID = 1, EMPIRE_STATE_ID = 2, TERRITORY_STATE_ID = 3, OPTIONS_STATE_ID = 4;
	
	
	
	public Game(String name) {
		super(name);
		
		addState(new MainMenu(MAIN_MENU_STATE_ID));
		addState(new WorldState(WORLD_STATE_ID));
		addState(new EmpireState(EMPIRE_STATE_ID));
		addState(new TerritoryState(TERRITORY_STATE_ID));
		addState(new Options(OPTIONS_STATE_ID));
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		for (int i = 0; i < getStateCount(); i ++){
			getState(i).init(container, this);
		}
		enterState(MAIN_MENU_STATE_ID);
	}
	
	public static void main(String[] args){
		try {
			AppGameContainer app = new AppGameContainer(new Game("TWClone"), WIDTH, HEIGHT, false);
			app.setTargetFrameRate(200);
			app.start();
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}

	
	
}

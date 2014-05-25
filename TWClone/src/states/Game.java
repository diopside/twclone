package states;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import entities.Faction;
import entities.world.Tile;

public class Game extends StateBasedGame{
	
	
	/*
	 * TO-DO:
	 * 
	 * 
	 *  - Orange is too difficult to distinguish from Yellow for faction colors, change this
	 *  - Make Faction flags more interesting
	 *  - Get the faction flag and any other interesting items rendering in the SUB_WINDOW in territory state
	 * 
	 * - Make the faction territories grouped together and scale them based off of the size of the game
	 * 
	 * 
	 * - change event of trying to find path to occupied tile to finding best close tile
	 * 
	 * - change pathfinding to sort the open list by best f
	 * 
	 * 
	 * 
	 */
	


	public static final int WIDTH = 1200, HEIGHT = 800;
	public static final Rectangle SCREEN = new Rectangle(0, 0, WIDTH, HEIGHT);
	//State IDs
	public static final int MAIN_MENU_STATE_ID = 0, WORLD_STATE_ID = 1, EMPIRE_STATE_ID = 2, TERRITORY_STATE_ID = 3, OPTIONS_STATE_ID = 4, TEST_STATE_ID = 5, END_TURN_ID = 6;
	public static TrueTypeFont MORRIS_ROMAN_18, MORRIS_ROMAN_24;
	public static Image CURSOR, BACKGROUND_1, BACKGROUND_2;
	
	
	public Game(String name) {
		super(name);
		
		addState(new MainMenu(MAIN_MENU_STATE_ID));
		addState(new WorldState(WORLD_STATE_ID));
		addState(new EmpireState(EMPIRE_STATE_ID));
		addState(new TerritoryState(TERRITORY_STATE_ID));
		addState(new Options(OPTIONS_STATE_ID));
		addState(new TestState(TEST_STATE_ID));
		addState(new EndTurnState(END_TURN_ID));
		
	}
	
	private static void initFonts(){
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/morrisroman.ttf");

			Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont1 = awtFont1.deriveFont(Font.BOLD, 18f); // set font size
			MORRIS_ROMAN_18 = new TrueTypeFont(awtFont1, true);

		} catch (Exception e) {
			e.printStackTrace();
		}   
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/morrisroman.ttf");

			Font awtFont1 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont1 = awtFont1.deriveFont(Font.BOLD, 24f); // set font size
			MORRIS_ROMAN_24 = new TrueTypeFont(awtFont1, true);

		} catch (Exception e) {
			e.printStackTrace();
		}  
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		for (int i = 0; i < getStateCount(); i ++){
			getState(i).init(container, this);
		}
		enterState(MAIN_MENU_STATE_ID);
		Tile.initTiles();
		Faction.initStaticMembers();

		initFonts();
		CURSOR = new Image("res/cursor.png");
		container.setMouseCursor(CURSOR, 1, 1);
		
		BACKGROUND_1 = new Image("res/background1.png");
		BACKGROUND_2 = new Image("res/background2.png");
		
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

package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.world.Territory;
import entities.world.Tile;
import entities.world.World;

public class TerritoryState extends BasicGameState {

	private final int SUB_WINDOW_MARGIN = 4;

	private final int ID;
	private Territory territory;
	private World world;
	private Image window;
	private int tX, tY; // the top left tile index used for rendering the top left baseview


	public TerritoryState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		initImages();
		world = ((WorldState) game.getState(Game.WORLD_STATE_ID)).getWorld();

	}

	private void initImages(){
		try {
			window = new Image("res/windows/territoryview.png");
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		window.draw();
		g.setColor(Color.black);   g.setBackground(Color.white);
		g.setFont(Game.MORRIS_ROMAN_24);
		g.drawString(territory.getName(), Game.WIDTH/2, 50);

		
		renderSubWindow();
		

	}
	
	private void renderSubWindow(){
		Tile[][] tiles = world.getMap().getTiles();
		for (int i = 0; i < 6; i ++)
			for (int j = 0; j < 6; j ++){
				// Get the tile, and then draw a tile corresponding to its type on the sub window
				Tile.tiles[tiles[tX + i][tY + j].getType()].draw(SUB_WINDOW_MARGIN + i * Tile.SIZE, SUB_WINDOW_MARGIN + j * Tile.SIZE);
				
				// test if the tile in question is where the base is located, if that is where the base is located draw it on top of the tile
				if (tiles[tX + i][tY + j].getY() == territory.getTiles().get(0).getY() && tiles[tX + i][tY + j].getX() == territory.getTiles().get(0).getX())
					territory.getIcon().getImage().draw(SUB_WINDOW_MARGIN + i * Tile.SIZE, SUB_WINDOW_MARGIN + j * Tile.SIZE);
			}
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



	public void setTerritory(World world, Territory t){
		territory = t;

		// This next block of code will set the tile x and y coordinates in order to render the small sub image in the top left
		
		Tile baseTile = t.getTiles().get(0);

		if (baseTile.getX() < 2)
			tX = 0;
		else if ((int) baseTile.getX() >= world.getMap().getSize() - 1 - 3)
			tX = world.getMap().getSize() - 6;
		else
			tX = baseTile.getX() - 2;

		if (baseTile.getY() < 2)
			tY = 0;
		else if ((int) baseTile.getY() >= world.getMap().getSize() - 1 - 3)
			tY = world.getMap().getSize() - 6;
		else
			tY = baseTile.getY() - 2;





	}

}

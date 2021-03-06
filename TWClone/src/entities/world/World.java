package entities.world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import states.Game;
import entities.Faction;

public class World {

	private Faction[] factions;
	private int year;
	private MapShape map;

	private World(Faction[] factions, int year){
		this.factions = factions;
		this.year = year;
		map = new MapShape(100);
		
		giveInitialTerritories();


	}
	private void giveInitialTerritories(){
		// Currently this method gives each faction two random territories
		for (Faction f: factions)
			while (f.getTerritories().size() < 2){
				Territory t = map.getTerritories()[(int)(Math.random() * map.getTerritories().length)];
				if (t.getOwner() == null){
					t.setOwner(f);
					f.addTerritory(t);
				}
			}
	}

	public void render(Graphics g, int xOffset, int yOffset){
		
		Rectangle offsetScreen = new Rectangle(xOffset, yOffset, Game.WIDTH, Game.HEIGHT);

		renderTiles(g, xOffset, yOffset);
		
		
		for (Territory t: map.getTerritories())
			if (t.getMaximumBoundaries().intersects(offsetScreen))
				t.render(g, xOffset, yOffset);
	}

	private void renderTiles(Graphics g, int xOffset, int yOffset){
		final int TOTAL_X = 40, TOTAL_Y = 28; // The total number of tiles to be rendered, should total out to around (on the larger side) the total pixels of the game screen
		
		int tX = determineTileRenderStart(xOffset, TOTAL_X);
		int tY = determineTileRenderStart(yOffset, TOTAL_Y);
		
		// where to start rendering on the screen, if you are rendering off the low boundary (negative offsets) you need to render starting further positive
		// namely the absolute value of the offset(s)
		int renderStartX = xOffset < 0 ? -1 * xOffset : - (xOffset % Tile.SIZE);
		int renderStartY = yOffset < 0 ? -1 * yOffset : - (yOffset % Tile.SIZE);
		
		// if you are rendering too far to the positive side of the array you must render fewer overall tiles to prevent overflow or drawing the wrong tiles
		int maxX = tX + TOTAL_X >= map.getSize() ? map.getSize() - tX : TOTAL_X;
		int maxY = tY + TOTAL_Y >= map.getSize() ? map.getSize() - tY : TOTAL_Y;
		
		for (int x = 0; x < maxX; x ++)
			for (int y = 0; y < maxY; y ++)
				map.getTiles()[x + tX][y + tY].render(renderStartX + x * Tile.SIZE, renderStartY + y * Tile.SIZE, g, xOffset, yOffset);
			
	}
	
	private int determineTileRenderStart(int offset, int totalToBeRendered){
		int start = offset / Tile.SIZE;
		if (start < 0)
			start = 0;
		return start;
	}

	//*************************************** Static Methods **********************************************
	public static World generateWorld(){
		World world;
		Faction[] fs = new Faction[4];

		fs[0] = new Faction("Faction-0", "red");
		fs[1] = new Faction("Faction-1", "yellow");
		fs[2] = new Faction("Faction-2", "pink");
		fs[3] = new Faction("Faction-3", "blue");

		world = new World(fs, 500);

		return world;
	}


	//********************************************* Getters and Setters ******************************************


	public Faction[] getFactions(){
		return factions;
	}

	public MapShape getMap(){
		return map;
	}
	public int getYear(){
		return year;
	}
	public void setYear(int i){
		year = i;
	}
	public Faction getPlayer(){
		return factions[0];
	}
}

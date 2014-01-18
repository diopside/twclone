package entities.world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

import states.Game;
import entities.Coordinates;
import entities.Faction;
import entities.Icon;
import entities.units.Army;
import entities.units.Unit;
import entities.units.buildings.Building;
import gui.OffsetLine;

public class Territory {



	private final short ID;
	private Faction owner;
	private final int type;  // this will represent whether it is a grassland, forest, desert, marsh, tundra or mountainous region
	private int populaltion;
	private Icon baseIcon;
	private ArrayList<OffsetLine> border;
	private ArrayList<Tile> tiles;
	private ArrayList<Building> buildings;
	private ArrayList<Building> buildingConstruction;
	private ArrayList<Unit> unitConstruction;
	private Rectangle maximumBoundaries;
	private Army garrison;
	private int foodVal;
	private int mineralVal;
	private int woodVal;




	public Territory(ArrayList<Tile> tiles, short id){
		ID = id;
		owner = null;
		type = (int) (Math.random() * 6);
		this.tiles = tiles;
		border = new ArrayList<>();

	}
	
	public void initIcon(){
		baseIcon = new Icon("res/icons/castle.png", tiles.get(0));
	}

	public void render(Graphics g, int xOffset, int yOffset){

		// Draw the city or "base"
		if (baseIcon.getShape(xOffset, yOffset).intersects(Game.SCREEN)){
			baseIcon.render(g, xOffset, yOffset, owner);
		}


		g.setLineWidth(4f);
		if (owner == null)
			g.setColor(Faction.NEUTRAL_COLOR);
		else
			g.setColor(owner.getColor());
		for (OffsetLine ol: border){
			Line line = ol.getOffsetLine(xOffset, yOffset);
			g.draw(line);
		}


	}
	
	public void setWoodVal(int w){
		woodVal = w;
	}
	
	public void setMineralVal(int m){
		mineralVal = m;
	}

	public void setFoodVal(int f){
		foodVal = f;
	}
	public void createMaximumBoundaries(){
		int maxX = 0, minX = Integer.MAX_VALUE, maxY = 0, minY = Integer.MAX_VALUE;
		Coordinates[] coords = new Coordinates[2];

		for (OffsetLine l: border){
			coords[0] = l.getPoint1();  
			coords[1] = l.getPoint2();
			for (Coordinates c: coords){
				maxX = c.getX() > maxX ? c.getX() : maxX;
				minX = c.getX() < minX ? c.getX() : minX;
				maxY = c.getY() > maxY ? c.getY() : maxY;
				minY = c.getY() < minY ? c.getY() : minY;
			}

		}

		maximumBoundaries = new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}

	public ArrayList<OffsetLine> getBorder(){
		return border;
	}

	public Rectangle getMaximumBoundaries(){
		return maximumBoundaries;
	}
	public Icon getIcon(){
		return baseIcon;
	}

	public Faction getOwner(){
		return owner;
	}

	public void setOwner(Faction f){
		this.owner = f;
		this.baseIcon.setOwner(f);
	}
	public boolean onScreen(int xOffset, int yOffset){
		return baseIcon.getShape(xOffset, yOffset).intersects(Game.SCREEN);
	}

	public boolean onBaseIcon(int mouseX, int mouseY, int xOffset, int yOffset){
		// This method will return true if the mouse is within the icon representing the base in the territory
		return baseIcon.getShape(xOffset, yOffset).contains(mouseX, mouseY);
	}


	public int getType(){
		// The type of territory it is, eg Desert
		return type;
	}


	public String getName(){
		return "Territory-"+ID;
	}


	public ArrayList<Tile> getTiles(){
		return this.tiles;
	}

	public short getID(){
	
		return ID;
	}
	
	public void processTurn(){
		
	}

	
	public String getResInfo(){
		float numTiles = tiles.size();
		String s = new String( "Food: " + foodVal + "    Wood: " + woodVal + "    Minerals: " + mineralVal);
		s += "\nFood Density: " + foodVal/numTiles + "      Wood Density: " + woodVal/numTiles + "    Mineral Density: " + mineralVal/numTiles;
		
		return s;
	}



}

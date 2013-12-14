package entities.world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

import states.Game;
import entities.Faction;
import entities.Icon;
import gui.OffsetLine;

public class Territory {
	
	private static Image NEUTRAL_FLAG;


	private final short ID;
	private Faction owner;
	private final int type;  // this will represent whether it is a grassland, forest, desert, marsh, tundra or mountainous region
	private int populaltion;
	private Icon baseIcon;
	private ArrayList<OffsetLine> border;
	private int x, y;
	private ArrayList<Tile> tiles;
	



	public Territory(ArrayList<Tile> tiles, short id){
		ID = id;
		owner = null;
		type = (int) (Math.random() * 6);
		this.x = tiles.get(0).getX() * Tile.SIZE;
		this.y = tiles.get(0).getY() * Tile.SIZE;
		baseIcon = new Icon("res/icons/castle.png", x, y);
		this.tiles = tiles;
		border = new ArrayList<>();
		
	}

	public void render(Graphics g, int xOffset, int yOffset){
		
		

		if (baseIcon.getShape(xOffset, yOffset).intersects(Game.SCREEN)){
			baseIcon.render(g, xOffset, yOffset);
		}
		
		g.setLineWidth(3f);
		g.setColor(Color.black);
		for (OffsetLine ol: border){
			Line line = ol.getOffsetLine(xOffset, yOffset);
			g.draw(line);
		}
		

	}

	public ArrayList<OffsetLine> getBorder(){
		return border;
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
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public ArrayList<Tile> getTiles(){
		return this.tiles;
	}

	public short getID(){
		return ID;
	}

	
	
	public static void initStaticMembers(){
		try {
			NEUTRAL_FLAG = new Image("res/icons/flags/white.png");
		} catch (SlickException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}
}

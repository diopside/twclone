package entities.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;

import states.Game;
import entities.Faction;

public class Territory {
	
	public static final int TERRITORY_SIZE = 256;
	
	private int x, y;
	private final short ID;
	private Faction owner;
	private Shape area;
	private final int type;  // this will represent whether it is a grassland, forest, desert, marsh, tundra or mountainous region
	
	public Territory(Shape shape, short id){
		ID = id;
		area = shape;
		owner = null;
		x = (int) shape.getX();
		y = (int) shape.getY();
		type = (int) (Math.random() * 6);
	}
	
	public void render(Graphics g, int xOffset, int yOffset){
		
		Rectangle newShape = new Rectangle(x - xOffset, y - yOffset, TERRITORY_SIZE, TERRITORY_SIZE);
		
		if (!newShape.intersects(Game.SCREEN)) // if not on screen do not render
			return;
		
		switch (type){
		case 0:
			g.setColor(Color.green);
			break;
		case 1: 
			g.setColor(Color.cyan);// will temprorarily be used to represent a forest
			break;
		case 2:
			g.setColor(Color.yellow);
			break;
		case 3:
			g.setColor(Color.blue);
			break;
		case 4:
			g.setColor(Color.lightGray);
			break;
		case 5:
			g.setColor(Color.darkGray);
		}
	
		g.fill(newShape);
		
		
		
		g.setColor(Color.black);
		g.setLineWidth(5f);
		g.draw(newShape);
		
	}
	
	
	

}

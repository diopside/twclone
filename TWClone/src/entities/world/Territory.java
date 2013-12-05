package entities.world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;

import states.Game;
import entities.Faction;
import entities.Icon;

public class Territory {

	public static final int TERRITORY_SIZE = 256;

	private int x, y;
	private final short ID;
	private Faction owner;
	private Shape area;
	private final int type;  // this will represent whether it is a grassland, forest, desert, marsh, tundra or mountainous region
	private int populaltion;
	private Icon baseIcon;



	public Territory(Shape shape, short id){
		ID = id;
		area = shape;
		owner = null;
		x = (int) shape.getX();
		y = (int) shape.getY();
		type = (int) (Math.random() * 6);
		baseIcon = new Icon("res/icons/castle.png", x + 100, y + 100);
	}

	public void render(Graphics g, int xOffset, int yOffset){



		if (onScreen(xOffset, yOffset)){
			Rectangle newShape = getRectangle(xOffset, yOffset);
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

			g.setColor(Color.black); // Draw the Borders
			g.setLineWidth(5f);
			g.draw(newShape);

			// draw the fort/castle
			baseIcon.render(g, xOffset, yOffset);
		}
	}

	public Rectangle getRectangle(int xOffset, int yOffset){
		// Returns the rectangle representing the territory itself
		return new Rectangle(x - xOffset, y - yOffset, TERRITORY_SIZE, TERRITORY_SIZE);
	}

	public boolean onScreen(int xOffset, int yOffset){
		// Returns true any part of the territory intersects the screen (and should be rendered for instance)
		return getRectangle(xOffset, yOffset).intersects(Game.SCREEN);
	}
	
	public boolean onBaseIcon(int mouseX, int mouseY, int xOffset, int yOffset){
		// This method will return true if the mouse is within the icon representing the base in the territory
		return baseIcon.getShape(xOffset, yOffset).contains(mouseX, mouseY);
	}


	public int getType(){
		// The type of territory it is, eg Desert
		return type;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public String getName(){
		return "Territory-"+ID;
	}


}

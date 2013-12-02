package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import states.Game;
import entities.world.Territory;
import entities.world.World;

public class MiniMap {
	
	private static final int START_X = 128, START_Y = 41 + 600, SIZE = 158;
	
	private Territory[] territories;
	
	public MiniMap(){
		
	}
	
	public void setTerritories(Territory[] t){
		territories = t;
	}

	public void render(Graphics g, int xOffset, int yOffset){
		float rSize = (float) (SIZE / 8.0); // divide by the number of territories per column or row in world square
		for (int i = 0; i < territories.length; i ++){
			
			Rectangle newShape = new Rectangle( (i % 8) * rSize + START_X, (i / 8) * rSize + START_Y, rSize, rSize );
			
			int type = territories[i].getType();
			
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
		} // end territory squares render
		
		
		
		
		
		// This next block of code will deal with rendering the a box representing the screen onto the minimap to give the player
		// a representation of which part of the map they are looking at.
		
		Rectangle window;
		float mapSize = Territory.TERRITORY_SIZE * 8;
		float xScale = Game.WIDTH / mapSize;
		float yScale = Game.HEIGHT / mapSize;
		
		float windowX = xOffset/mapSize * SIZE;
		float windowY = yOffset/mapSize * SIZE;
		
		window = new Rectangle(windowX + START_X, windowY + START_Y, xScale * SIZE, yScale * SIZE);
		
		g.setColor(Color.black);
		g.setLineWidth(2.5f);
		
		g.draw(window);
	}
	
	
	
	
	

}

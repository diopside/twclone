package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import states.Game;
import states.WorldState;
import entities.world.Territory;
import entities.world.Tile;
import entities.world.World;

public class MiniMap {

	private static final int START_X = 128, START_Y = 41 + 600, SIZE = 158;
	private static Color forest = new Color(0x264200), plains = new Color(0x26BE00), desert = new Color(0xFFE97F), 
			tundra = new Color(0xE2E2E2), mountains = new Color(0x404040), marsh = new Color(0x007F46);
	private float arrLength;
	private ArrayList<ColoredShape> lines;

	public MiniMap(){
		lines = new ArrayList<>();
	}

	public void setInformation(Tile[][] t, int length){
		arrLength = length;
		float rSize = (float) (SIZE / arrLength);
		
		// This block of code will create a new Line and Color pair to correspond with each tile on the map
		// This pair will then be rendered to represent the minimap.
		for (int i = 0; i < arrLength; i ++){
			for (int j = 0; j < arrLength; j ++){
				Tile tile = t[i][j];
				Color color = null;
				switch (tile.getType()){
				case 0:
					color = plains; break;
				case 1: 
					color = forest; break;
				case 2:
					color = desert; break;
				case 3:
					color = marsh; break;
				case 4:
					color = tundra; break;
				case 5:
					color = mountains; break;
				}
				Line line = new Line(i * rSize + START_X, j * rSize + START_Y, (i + 1) * rSize + START_X, (j+1)*rSize + START_Y);
				lines.add(new ColoredShape(line, color));
				
			}
		}
	}

	public void render(Graphics g, int xOffset, int yOffset){
		float rSize = (float) (SIZE / arrLength);
		g.setLineWidth(rSize);
		for (ColoredShape cs: lines){
			g.setColor(cs.getColor());
			g.draw(cs.getShape());
		}


		// This next block of code will deal with rendering the a box representing the screen onto the minimap to give the player
		// a representation of which part of the map they are looking at.
		Rectangle window;
		float mapSize = Tile.SIZE * arrLength;
		float xScale = Game.WIDTH / mapSize;
		float yScale = Game.HEIGHT / mapSize;

		float windowX = xOffset/mapSize * SIZE;
		float windowY = yOffset/mapSize * SIZE;

		window = new Rectangle(windowX + START_X, windowY + START_Y, xScale * SIZE, yScale * SIZE);

		// Make the window representing the screen black and draw it
		g.setColor(Color.black); 
		g.setLineWidth(2.5f);
		g.draw(window);
	}


	public Rectangle getRectangle(){
		return new Rectangle(START_X, START_Y, SIZE, SIZE);
	}

	public void click(float mouseX, float mouseY, WorldState worldState) {
		float mapX = (mouseX - START_X) / SIZE * (arrLength * Tile.SIZE) - Game.WIDTH/2;
		float mapY = (mouseY - START_Y) / SIZE * (arrLength * Tile.SIZE) - Game.HEIGHT/2;
		worldState.setOffsets( (int) mapX, (int) mapY);
	}






}

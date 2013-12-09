package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import states.Game;
import entities.world.Territory;
import entities.world.Tile;
import entities.world.World;

public class MiniMap {

	private static final int START_X = 128, START_Y = 41 + 600, SIZE = 158;
	private static Color forest = new Color(0x264200), plains = new Color(0x26BE00), desert = new Color(0xFFE97F), 
			tundra = new Color(0xE2E2E2), mountains = new Color(0x404040), marsh = new Color(0x007F46);
	private float arrLength;
	private byte[][] pixels;

	public MiniMap(){

	}

	public void setInformation(Tile[][] t, int length){
		arrLength = length;
		pixels = new byte[length][length];
		for (int i = 0; i < arrLength; i ++){
			for (int j = 0; j < arrLength; j ++){
				Tile tile = t[i][j];
				pixels[i][j] = (byte) tile.getType();

			}
		}
	}

	public void render(Graphics g, int xOffset, int yOffset){
		float rSize = (float) (SIZE / arrLength);
		for (int i = 0; i < arrLength; i ++){
			for (int j = 0; j < arrLength; j ++){
				int type = pixels[i][j];
				switch (type){
				case 0:
					g.setColor(plains);
					break;
				case 1: 
					g.setColor(forest);// will temporarily be used to represent a forest
					break;
				case 2:
					g.setColor(desert);
					break;
				case 3:
					g.setColor(marsh);
					break;
				case 4:
					g.setColor(tundra);
					break;
				case 5:
					g.setColor(mountains);
				}

				g.setLineWidth(rSize);
				g.drawLine(i * rSize + START_X, j * rSize + START_Y, (i + 1) * rSize + START_X, (j+1)*rSize + START_Y);
			}
		} // end territory squares render





		// This next block of code will deal with rendering the a box representing the screen onto the minimap to give the player
		// a representation of which part of the map they are looking at.

		Rectangle window;
		float mapSize = Tile.SIZE * arrLength;
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

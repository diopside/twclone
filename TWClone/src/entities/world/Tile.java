package entities.world;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import states.Game;

public class Tile {

	public static Image[] tiles; // static holder for tile images
	public static final int SIZE = 32; // size of the tiles
	
	private byte id, x, y;
	
	public Tile(byte x, byte y, byte id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	
	
	






	public void render(Graphics g, int xOffset, int yOffset){
		if (getRectangle(xOffset, yOffset).intersects(Game.SCREEN)){
			tiles[id].draw(x * SIZE - xOffset, y * SIZE - yOffset);
			g.setColor(Color.black);
			//g.draw(new Rectangle(x * SIZE - xOffset, y * SIZE - yOffset, SIZE, SIZE));
		}
	}
	
	public Rectangle getRectangle(int xOffset, int yOffset){
		return new Rectangle(x * SIZE, y * SIZE, SIZE, SIZE);
	}
	
	
	
	public static void initTiles(){
		tiles = new Image[24];
		try{
			tiles[0] = new Image("res/tiles/grass.png");
			tiles[1] = new Image("res/tiles/forest.png");
			tiles[2] = new Image("res/tiles/forest.png");
			tiles[3] = new Image("res/tiles/forest.png");
			tiles[4] = new Image("res/tiles/forest.png");
			tiles[5] = new Image("res/tiles/forest.png");
		}
		catch (SlickException e){
			e.printStackTrace();
		}
		finally{
			for (int i = 1; i < 4; i ++)
				for (int j = 0; j < 6; j ++){
					if (i == 1)
						tiles[i*6 + j] = tiles[j].getFlippedCopy(true, false);
					else if (i == 2)
						tiles[i*6 + j] = tiles[j].getFlippedCopy(true, true);
					else
						tiles[i*6 + j] = tiles[i * 6 - 6 + j].getFlippedCopy(true, true);
						
				}
			
		}

	} // end tile init

}

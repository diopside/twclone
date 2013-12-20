package entities.world;

import java.util.ArrayList;

import entities.Entity;
import gui.ToolTip;
import gui.ToolTippable;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import states.Game;

public class Tile implements ToolTippable {

	public static Image[] tiles; // static holder for tile images
	public static final int SIZE = 32; // size of the tiles

	private int id, x, y;
	private Territory territory;
	private Entity occupyingEntity;

	public Tile(int x, int y, int id){
		this.x = x;
		this.y = y;
		this.id = id;
		territory = null;
	}







	public void setOccupyingEntity(Entity e){
		occupyingEntity = e;
	}
	public boolean occupied(){
		return occupyingEntity != null;
	}

	public void setTerritory(Territory t){
		territory = t;
	}
	public Territory getTerritory(){
		return territory;
	}

	public void render(Graphics g, int xOffset, int yOffset){
		if (getRectangle(xOffset, yOffset).intersects(Game.SCREEN)){
			tiles[id].draw(x * SIZE - xOffset, y * SIZE - yOffset);
			if (occupied()){
				g.setColor(Color.pink);
				g.draw(getRectangle(xOffset, yOffset));
			}
		}
	}
	
	public void render(int x, int y, Graphics g, int xOffset, int yOffset){
		tiles[id].draw(x, y);
		if (occupied()){
			g.setColor(Color.pink);
			g.draw(getRectangle(xOffset, yOffset));
		}
	}

	public Rectangle getRectangle(int xOffset, int yOffset){
		return new Rectangle(x * SIZE - xOffset, y * SIZE - yOffset, SIZE, SIZE);
	}

	public double distance(Tile t){
		return Math.sqrt(  (this.x - t.getX())*(this.x - t.getX()) + (this.y - t.getY())*(this.y - t.getY()) );
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

	public void setID(int id){
		this.id = id;
	}
	public int getID(){
		return id;
	}
	public int getType(){
		// this will be used by the minimap
		return id % 6;
	}
	
	


	public static void initTiles(){
		tiles = new Image[24];
		try{
			tiles[0] = new Image("res/tiles/grass.png");
			tiles[1] = new Image("res/tiles/forest.png");
			tiles[2] = new Image("res/tiles/desert.png");
			tiles[3] = new Image("res/tiles/marsh.png");
			tiles[4] = new Image("res/tiles/snow.png");
			tiles[5] = new Image("res/tiles/mountain.png");

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

	@Override
	public void setToolTip(ToolTip tt) {
		tt.setX(this.x * SIZE + SIZE);
		tt.setY(this.y * SIZE + SIZE);
		
		ArrayList<String> message = new ArrayList<>();
		message.add(territory.getName());
		switch (id % 6){
			case 0: message.add( "\nPlains territory"); break; 
			case 1: message.add( "\nForest territory"); break;
			case 2: message.add( "\nDesert territory"); break;
			case 3: message.add( "\nMarsh territory"); break;
			case 4: message.add( "\nTundra territory"); break;
			case 5: message.add( "\nMountain territory"); break;
		}


		tt.setMessage(message);
		tt.setActive(!tt.isActive());

	}


}

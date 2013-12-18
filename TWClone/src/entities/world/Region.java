package entities.world;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class Region {
	
	/*
	 * This class will represent large areas of similar landforms called regions
	 */
	
	private ArrayList<Tile> tiles;
	private int type;
	private String name;
	
	public Region(int type){
		tiles = new ArrayList<>();
		this.type = type;
	}
	
	public void render(Graphics g, int xOffset, int yOffset){
		for (Tile t: tiles)
			t.render(g, xOffset, yOffset);
		
		
	}
	
	public int getType(){
		return type;
	}
	
	public ArrayList<Tile> getTiles(){
		return tiles;
	}
	
	public void addTile(int x, int y){
		for (int i = 0; i < 8; i ++){
			for (int j = 0; j < 8; j++){
				int id = type + (6 *((int) (Math.random() * 4)));
				tiles.add(new Tile( (byte) (x + i),(byte) (y + j),(byte) id ));
			}
		}
	}
	
	public void addTile(Tile t){
		// The 6 and 4 represent the number of tile types and the number of different tiles for each type respectively
		t.setID(type + (6 *((int) (Math.random() * 4))));
		tiles.add(t);
		
	}
	
	
	

}

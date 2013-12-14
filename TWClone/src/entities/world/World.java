package entities.world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import entities.Faction;

public class World {

	private Faction[] factions;
	private int year;
	private MapShape map;
	
	private World(Faction[] factions, int year){
		this.factions = factions;
		this.year = year;
		map = new MapShape(100);
	}
	
	
	public void render(Graphics g, int xOffset, int yOffset){
		
		for (Region r: map.getRegions())
			r.render(g, xOffset, yOffset);
		for (Territory t: map.getTerritories())
			t.render(g, xOffset, yOffset);
	}
	
	
	
	
	
	
	
	//*************************************** Static Methods **********************************************
	public static World generateWorld(){
		World world;
		Faction[] fs = new Faction[4];
		
		
		for (int i = 0; i < fs.length; i ++)
			fs[i] = new Faction(""+i);
		
		world = new World(fs, 500);
		
		return world;
	}
	
	//********************************************* Getters and Setters ******************************************
	
	
	
	public MapShape getMap(){
		return map;
	}
	
}

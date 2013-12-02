package entities.world;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import entities.Faction;

public class World {

	private Territory[] territories;
	private Faction[] factions;
	private int year;
	
	private World(Territory[] territories, Faction[] factions, int year){
		this.territories = territories;
		this.factions = factions;
		this.year = year;
	}
	
	
	public void render(Graphics g, int xOffset, int yOffset){
		
		for (Territory t: territories){
			t.render(g, xOffset, yOffset);
		}
	}
	
	
	
	
	
	
	
	//*************************************** Static Methods **********************************************
	public static World generateWorld(){
		World world;
		Territory[] ts = new Territory[64];
		Faction[] fs = new Faction[4];
		
		for (int i = 0; i <  ts.length ; i ++){
			Rectangle r = new Rectangle( (i % 8) * Territory.TERRITORY_SIZE, (i / 8) * Territory.TERRITORY_SIZE, Territory.TERRITORY_SIZE, Territory.TERRITORY_SIZE);
			ts[i] = new Territory(r, (short) i);
		}
		for (int i = 0; i < fs.length; i ++)
			fs[i] = new Faction();
		
		world = new World(ts, fs, 500);
		
		return world;
	}
	
	//********************************************* Getters and Setters ******************************************
	
	public Territory[] getTerritories(){
		return territories;
	}
	
}

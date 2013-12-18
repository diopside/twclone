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
		map = new MapShape(80);
		giveInitialTerritories();


	}
	private void giveInitialTerritories(){
		for (Faction f: factions){
			while (f.getTerritories().size() < 2){
				Territory t = map.getTerritories()[(int)(Math.random() * map.getTerritories().length)];
				if (t.getOwner() == null){
					t.setOwner(f);
					f.addTerritory(t);
				}
			}

		}
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

		fs[0] = new Faction("Faction-0", "red");
		fs[1] = new Faction("Faction-1", "yellow");
		fs[2] = new Faction("Faction-2", "pink");
		fs[3] = new Faction("Faction-3", "blue");

		world = new World(fs, 500);

		return world;
	}


	//********************************************* Getters and Setters ******************************************


	public Faction[] getFactions(){
		return factions;
	}

	public MapShape getMap(){
		return map;
	}
	public int getYear(){
		return year;
	}
	public Faction getPlayer(){
		return factions[0];
	}
}

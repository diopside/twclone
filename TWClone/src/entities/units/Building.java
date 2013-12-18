package entities.units;

import entities.Faction;
import entities.world.Territory;

abstract public class Building{

	private String name;
	
	public abstract boolean canBuild(Faction f, Territory t);
	public abstract void build(Faction f, Territory t);
	
}

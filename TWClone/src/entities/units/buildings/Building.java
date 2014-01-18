package entities.units.buildings;

import entities.Faction;
import entities.world.Territory;

abstract public class Building{

	protected String name;
	protected byte level;
	
	
	public abstract boolean canBuild(Faction f, Territory t);
	public abstract void build(Faction f, Territory t);
	
	
}

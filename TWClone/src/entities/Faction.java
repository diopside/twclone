package entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import entities.units.*;
import entities.world.Territory;

public class Faction {

	private ArrayList<Territory> territories;
	private ArrayList<Unit> units;
	private ArrayList<Army> armies;
	private int gold, wood, food, minerals, mana;
	private String name;
	private Image crest; // This will represent the coat of arms, or flag of the faction
	
	
	public Faction(String name){
		territories = new ArrayList<>();
		units = new ArrayList<>();
		armies = new ArrayList<>();
		gold = food = wood = minerals = 100;
		this.name = name;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getName(){
		return name;
	}
	
	
	
}

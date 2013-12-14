package entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.units.*;
import entities.world.Territory;

public class Faction {

	

	public static Image NEUTRAL_FLAG;
	public static final Color NEUTRAL_COLOR = Color.black;
	
	private ArrayList<Territory> territories;
	private ArrayList<Unit> units;
	private ArrayList<Army> armies;
	private int gold, wood, food, minerals, mana;
	private String name;
	private Image flag; // This will represent the coat of arms, or flag of the faction
	private Color color;
	
	
	public Faction(String name, String color){
		territories = new ArrayList<>();
		units = new ArrayList<>();
		armies = new ArrayList<>();
		gold = food = wood = minerals = 100;
		this.name = name;
		setColor(color);
		
		initImages(color);
	}
	private void initImages(String color){
		try {
			flag = new Image("res/icons/flags/"+color+".png");
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}
	private void setColor(String color){
		switch(color){
		case "red":
			this.color = Color.red; break;
		case "blue":
			this.color = Color.blue; break;
		case "orange":
			this.color = Color.orange; break;
		case "yellow":
			this.color = Color.yellow; break;
		case "pink":
			this.color = Color.pink; break;
		}
	}
	
	
	
	
	
	
	
	public void addTerritory(Territory t){
		territories.add(t);
	}
	public ArrayList<Territory> getTerritories(){
		return territories;
	}
	
	public Image getFlag(){
		return flag;
	}
	
	public Color getColor(){
		return color;
	}
	
	
	public String getName(){
		return name;
	}
	
	
	public static void initStaticMembers(){
		try {
			NEUTRAL_FLAG = new Image("res/icons/flags/white.png");
		} catch (SlickException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}
	
	
}

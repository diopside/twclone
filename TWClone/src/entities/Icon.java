package entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import entities.units.Unit;
import entities.world.Territory;
import entities.world.Tile;

public class Icon extends Entity {

	
	/*
	 * An item rendered on the map to represent some kind of unit, such as a base in a territory or an army for example.
	 */
	
	private Image icon;
	
	public Icon(String dir, int x, int y, Faction owner){
		initImage(dir);
		coord = new Coordinates(x, y);
		this.owner = owner;
	}
	
	public Icon(String dir, Tile t){
		initImage(dir);
		coord = new Coordinates(t.getX(), t.getY());
		this.owner = t.getTerritory().getOwner();
		t.setOccupyingEntity(this);
	}
	
	private void initImage(String dir){
		try {
			icon = new Image(dir);
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}
	
	public void setOwner(Faction f){
		this.owner = f;
	}
	
	public Shape getShape(int xOffset, int yOffset){
		return new Rectangle(x() * Tile.SIZE - xOffset, y() * Tile.SIZE - yOffset, icon.getWidth(), icon.getHeight());
	}
	
	public Image getImage(){
		return icon;
	}
	
	
	
	public void render(Graphics g, int xOffset, int yOffset, Faction f){
		icon.draw(x() * Tile.SIZE - xOffset, y() * Tile.SIZE - yOffset);
		if (f == null){
			f.NEUTRAL_FLAG.draw(x() * Tile.SIZE-xOffset + icon.getWidth()/2f, y() * Tile.SIZE - f.NEUTRAL_FLAG.getHeight() - yOffset); 
		}
		else {
			f.getFlag().draw(x() * Tile.SIZE-xOffset + icon.getWidth()/2f, y() * Tile.SIZE - f.NEUTRAL_FLAG.getHeight() - yOffset);
		}
	}

	@Override
	public ArrayList<String> getToolTip() {
		ArrayList<String> strings = new ArrayList<>();
		
		strings.add("");
		if (owner == null)
			strings.add("Independent city");
		else
			strings.add("City controlled by the " + owner.getName() + " faction.");
		
		return strings;
		
	}
	
	
	
	
}

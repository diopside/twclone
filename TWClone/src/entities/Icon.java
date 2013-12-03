package entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import entities.units.Unit;
import entities.world.Territory;

public class Icon {

	
	/*
	 * An item rendered on the map to represent some kind of unit, such as a base in a territory or an army for example.
	 */
	
	private Image icon;
	private int x, y;
	
	public Icon(String dir, int x, int y){
		initImage(dir);
		this.x = x;
		this.y = y;
	}
	
	private void initImage(String dir){
		try {
			icon = new Image(dir);
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}
	
	public Shape getShape(int xOffset, int yOffset){
		return new Rectangle(x - xOffset, y - yOffset, icon.getWidth(), icon.getHeight());
	}
	
	
	
	public void render(Graphics g, int xOffset, int yOffset){
		icon.draw(x - xOffset, y - yOffset);
	}
	
	
	
	
}

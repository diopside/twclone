package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

abstract public class Window {

	
	protected Image pane;
	protected int x, y;
	protected boolean focus;
	
	
	
	public abstract void deselect();
	
	public boolean hasFocus(){
		return focus;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return y;
	}
	
	public abstract void init();
	
	public abstract void reset();
	
	public abstract void render(Graphics g, int xOffset, int yOffset);
		
	
	
	
	
	
	
	
}

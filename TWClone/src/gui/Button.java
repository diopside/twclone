package gui;

import org.newdawn.slick.Graphics;

abstract class Button {

	protected int x, y;

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public abstract void render(Graphics g, int xOffset, int yOffset);
	
}

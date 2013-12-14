package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class ToolTip {

	private Image window;
	private String message;
	private boolean active;
	private int x, y;
	
	public ToolTip(int x, int y, String  message){
		this.x = x;
		this.y = y;
		this.message = message;
		active = false;
		initImage();
	}
	
	private void initImage(){
		try {
			window = new Image("res/menus/tooltip.png");
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}
	
	public Rectangle getShape(int xOffset, int yOffset){
		return new Rectangle(x - xOffset, y - yOffset, window.getWidth(), window.getHeight());
	}
	
	public void render(Graphics g, int xOffset, int yOffset){
		window.draw(x - xOffset, y - yOffset);
		g.setColor(Color.black);
		
		g.drawString(message, x - xOffset + 10, y - yOffset + 10);
	}
	
	public boolean isActive(){
		return active;
	}
	public void setActive(boolean b){
		active = b;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}

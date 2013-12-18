package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import states.Game;
import entities.Coordinates;
import entities.Draggable;

public class ToolTip implements Draggable{

	private Image window;
	private ArrayList<String> messages;
	private boolean active;
	private Coordinates coord;
	private boolean dragging;

	public ToolTip(int x, int y, ArrayList<String> message){
		coord = new Coordinates(x, y);
		messages = message;
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
		return new Rectangle(x()- xOffset, y() - yOffset, window.getWidth(), window.getHeight());
	}

	public void render(Graphics g, int xOffset, int yOffset){
		float alpha = (dragging) ? .6f : 1f;
		window.setAlpha(alpha);
		window.draw(x() - xOffset, y() - yOffset);
		g.setColor(Color.black);

		g.setFont(Game.MORRIS_ROMAN_24);
		for (int i = 0; i < messages.size(); i ++){
			g.drawString(messages.get(i), x() - xOffset + 10, y() - yOffset + 10 + 20*i);
		}
	}

	private int x(){
		return coord.getX();
	}
	private int y(){
		return coord.getY();
	}

	public boolean isActive(){
		return active;
	}
	public void setActive(boolean b){
		active = b;
	}

	public ArrayList<String> getMessage() {
		return this.messages;
	}

	public void setMessage(ArrayList<String> message) {
		this.messages = message;
	}

	public int getX() {
		return x();
	}

	public void setX(int x) {
		coord.setX(x);
	}

	public int getY() {
		return y();
	}

	public void setY(int y) {
		coord.setY(y);
	}

	@Override
	public Shape getOffsetShape(int mouseX, int mouseY) {
		return getShape(mouseX, mouseY);
	}

	@Override
	public Coordinates getCoordinates() {
		return coord;
	}



	@Override
	public void setDragging(boolean b) {
		dragging = b;

	}

	@Override
	public void drag(Coordinates offCoords, int mouseX, int mouseY) {
		coord.setX(mouseX - offCoords.getX());
		coord.setY(mouseY - offCoords.getY());
	}


}

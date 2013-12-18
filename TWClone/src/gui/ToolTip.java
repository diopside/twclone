package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import entities.Coordinates;
import entities.Draggable;

public class ToolTip implements Draggable{

	private Image window;
	private String message;
	private boolean active;
	private Coordinates coord;
	private boolean dragging;

	public ToolTip(int x, int y, String  message){
		coord = new Coordinates(x, y);
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
		return new Rectangle(x()- xOffset, y() - yOffset, window.getWidth(), window.getHeight());
	}

	public void render(Graphics g, int xOffset, int yOffset){
			float alpha = (dragging) ? .6f : 1f;
			window.setAlpha(alpha);
			window.draw(x() - xOffset, y() - yOffset);
			g.setColor(Color.black);
		

			g.drawString(message, x() - xOffset + 10, y() - yOffset + 10);
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

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
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

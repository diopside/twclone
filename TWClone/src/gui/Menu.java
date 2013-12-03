package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import states.Game;
import entities.world.Territory;

public class Menu {
	
	
	private Image window;
	private ArrayList<Button> buttons;
	private int x, y;
	private final int CLOSE_BOX_X, CLOSE_BOX_Y, CLOSE_BOX_SIZE;
	private boolean active;
	private String header;
	
	
	
	public Menu(String dir, int closeX, int closeY, int closeSize){
		buttons = new ArrayList<>();
		initImages(dir);
		
		CLOSE_BOX_X = closeX;
		CLOSE_BOX_Y = closeY;
		CLOSE_BOX_SIZE = closeSize;
		active = false;
	}
	
	private void initImages(String dir){
		try {
			window = new Image(dir);
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}
	
	public void setActive(boolean b){
		active = b;
	}
	public boolean isActive(){
		return active;
	}
	
	public Rectangle getCloseRectangle(int xOffset, int yOffset){
		return new Rectangle(x + CLOSE_BOX_X - xOffset, y + CLOSE_BOX_Y - yOffset, CLOSE_BOX_SIZE, CLOSE_BOX_SIZE);
	}
	
	public void selectTerritory(Territory t){
		final int X_SPACING = 50;
		this.x = t.getX() + X_SPACING;
		this.y = t.getY();
		header = t.getName();
		active = true;
		
		final int BUTTON_MARGIN = 2;
		final int BUTTON_START_Y = 34;
		
		Button b = new BasicButton(x + BUTTON_MARGIN, y + BUTTON_MARGIN + BUTTON_START_Y, "res/buttons/territoryviewbtn.png");
		buttons.add(b);
		
	}
	
	public void deselect(){
		buttons.clear();
		header = "";
		active = false;
	}
	
	public void checkButtons(int mouseX, int mouseY, int xOffset, int yOffset, StateBasedGame game){
		for (Button b: buttons){
			if (b instanceof BasicButton){
				if (((BasicButton) b).offsetContains(mouseX, mouseY, xOffset, yOffset));
			}
		}
	}
	
	
	public void render(Graphics g, int xOffset, int yOffset){
		window.draw(x - xOffset, y - yOffset);
		g.setColor(Color.black);
		g.drawString(header, x + 5 - xOffset, y + 5 - yOffset);
		
		for (Button b: buttons){
			b.render(g, xOffset, yOffset);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}

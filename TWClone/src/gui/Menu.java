package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import states.Game;
import states.TerritoryState;
import entities.world.Territory;

public class Menu {
	
	
	/* This class will be used to bring up menus when a territory is selected which will be located
	 * Next to the base in the territory
	 */
	
	

	private final int CLOSE_BOX_X, CLOSE_BOX_Y, CLOSE_BOX_SIZE; // Constants based off the image file.
	
	private Image window;
	private ArrayList<Button> buttons;
	private int x, y;
	private boolean active; // Active will represent whether or not the menu should be rendered or not
	private String header;
	private Territory t;
	
	
	
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
		// This will return the rectangle containing the button to close the window
		return new Rectangle(x + CLOSE_BOX_X - xOffset, y + CLOSE_BOX_Y - yOffset, CLOSE_BOX_SIZE, CLOSE_BOX_SIZE);
	}
	
	public void selectTerritory(Territory t){
		this.t = t;
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
		// The buttons must be cleared or else it will continue to render old buttons once the menu is moved
		buttons.clear();
		header = "";
		active = false;
	}
	
	public void checkButtons(int mouseX, int mouseY, int xOffset, int yOffset, StateBasedGame game){
		// As of right now the only clickable button will be the one to enter the territory view.
		for (Button b: buttons){
			if (b instanceof BasicButton){ // Basic Buttons are clickable, the other ones will not be and will be used to display information
				if (((BasicButton) b).offsetContains(mouseX, mouseY, xOffset, yOffset)){
					TerritoryState ts = (TerritoryState) game.getState(Game.TERRITORY_STATE_ID);
					ts.setTerritory(t);
					game.enterState(Game.TERRITORY_STATE_ID);
				}
			}
		}
	}
	
	
	public void render(Graphics g, int xOffset, int yOffset){
		window.draw(x - xOffset, y - yOffset);
		g.setColor(Color.black);
		g.drawString(header, x + 5 - xOffset, y + 5 - yOffset); // The 5's are so the String isn't drawn on the margins of the window
		
		for (Button b: buttons){
			b.render(g, xOffset, yOffset);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}

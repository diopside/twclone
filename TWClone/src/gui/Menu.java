package gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import states.Game;
import states.TerritoryState;
import entities.Coordinates;
import entities.Draggable;
import entities.world.Territory;
import entities.world.World;

public class Menu implements Draggable {


	/* This class will be used to bring up menus when a territory is selected which will be located
	 * Next to the base in the territory
	 */



	private final int CLOSE_BOX_X, CLOSE_BOX_Y, CLOSE_BOX_SIZE; // Constants based off the image file.
	private final int HEADER_SIZE_X, HEADER_SIZE_Y; // used for the top of the menu for dragging

	private Image window;
	private ArrayList<Button> buttons;
	private Coordinates coord;
	private boolean active; // Active will represent whether or not the menu should be rendered or not
	private String header;
	private Territory t;
	private World world;
	private boolean dragging;



	public Menu(String dir, int closeX, int closeY, int closeSize, int headerX, int headerY, World world){
		buttons = new ArrayList<>();
		initImages(dir);
		coord = new Coordinates();

		CLOSE_BOX_X = closeX;
		CLOSE_BOX_Y = closeY;
		CLOSE_BOX_SIZE = closeSize;
		HEADER_SIZE_X = headerX;
		HEADER_SIZE_Y = headerY;
		active = false;
		
		this.world = world;
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
		return new Rectangle(x() + CLOSE_BOX_X - xOffset, y() + CLOSE_BOX_Y - yOffset, CLOSE_BOX_SIZE, CLOSE_BOX_SIZE);
	}

	public void selectTerritory(Territory t){
		this.t = t;
		final int X_SPACING = 100;
		coord.setX(t.getX() + X_SPACING);
		coord.setY(t.getY());
		header = t.getName();
		active = true;
		
		if (world.getPlayer().getTerritories().contains(t))
			generateButtons();

		
	}
	
	private void generateButtons(){
		final int BUTTON_MARGIN = 2;
		final int BUTTON_START_Y = 34;

		Button b = new BasicButton(x() + BUTTON_MARGIN, y() + BUTTON_MARGIN + BUTTON_START_Y, "res/buttons/territoryviewbtn.png");
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
					ts.setTerritory(world, t);
					game.enterState(Game.TERRITORY_STATE_ID);
				}
			}
		}
	}


	public void render(Graphics g, int xOffset, int yOffset){


		float alpha = dragging ? .6f : 1f;
		window.setAlpha(alpha);
		window.draw(x() - xOffset, y() - yOffset);
		g.setColor(Color.black);
		g.setFont(Game.MORRIS_ROMAN_24);
		g.drawString(header, x() + 5 - xOffset, y() + 5 - yOffset); // The 5's are so the String isn't drawn on the margins of the window

		for (Button b: buttons){
			b.render(g, xOffset, yOffset, alpha/2f);
		}
	}


	private int x(){
		return coord.getX();
	}
	private int y(){
		return coord.getY();
	}

	@Override
	public Shape getOffsetShape(int xOffset, int yOffset) {
		return new Rectangle(x() - xOffset, y() - yOffset, HEADER_SIZE_X, HEADER_SIZE_Y);
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
		int dx = mouseX - offCoords.getX();
		int dy = mouseY - offCoords.getY();
		coord.setX(dx);
		coord.setY(dy);
		
		buttons.clear();
		
		if (world.getPlayer().getTerritories().contains(t))
			generateButtons();
	}













}

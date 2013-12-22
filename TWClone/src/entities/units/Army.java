package entities.units;

import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import states.Game;
import entities.Coordinates;
import entities.Division;
import entities.Faction;
import entities.Leader;
import entities.PathGenerator;
import entities.world.Tile;

public class Army extends Unit{


	private Leader leader;
	private ArrayList<Division> troops;
	private Image image;

	public Army(Tile t, Faction faction, String dir, int armyNum){

		this.name = "Army " + armyNum;
		this.coord = new Coordinates(t.getX(), t.getY()); // Tile based coordinates
		this.owner = faction;
		path = new Stack<>();
		
		t.setOccupyingEntity(this);
		initImage(dir);
	}

	private void initImage(String dir){
		try {
			image = new Image(dir);
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void drag(Coordinates offCoords, int mouseX, int mouseY) {
		// Currently nothing, call a setDestination() method to create a path to the tile
		
	}


	@Override
	public Shape getOffsetShape(int xOffset, int yOffset) {
		return new Rectangle(x() * Tile.SIZE - xOffset, y() * Tile.SIZE - yOffset, image.getWidth(), image.getHeight());
	}

	@Override
	public void render(Graphics g, int xOffset, int yOffset, int mouseX,
			int mouseY) {
		
		if (getOffsetShape(xOffset, yOffset).intersects(Game.SCREEN) || dragging){
			if (dragging){
				image.setAlpha(.75f);
				image.draw(mouseX, mouseY, image.getWidth(), image.getHeight());
			}
			else{
				image.setAlpha(1f);
				image.draw(x() * Tile.SIZE - xOffset, y() * Tile.SIZE - yOffset, image.getWidth(), image.getHeight());
			}
		}

	}




}

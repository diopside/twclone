package entities.units;

import java.util.ArrayList;
import java.util.PriorityQueue;

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
	private PriorityQueue<Coordinates> path;
	private Image image;

	public Army(int tX, int tY, Faction faction, String dir){

		this.coord = new Coordinates(tX, tY); // Tile based coordinates
		this.owner = faction;

		initImage(dir);
	}

	private void initImage(String dir){
		try {
			image = new Image(dir);
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}




	public void setDestination(int tX, int tY){
		path = PathGenerator.generatePath(x(), y(), tX, tY);
	}

	public void setDestination(Tile destination){
		path = PathGenerator.generatePath(x(), y(), destination.getX(), destination.getY());
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
		
		g.setColor(Color.cyan);
		g.draw(getOffsetShape(xOffset, yOffset));
		if (getOffsetShape(xOffset, yOffset).intersects(Game.SCREEN)){
			if (dragging){
				image.setAlpha(.75f);
				image.draw(mouseX + xOffset, mouseY + yOffset, image.getWidth(), image.getHeight());
			}
			else{
				image.setAlpha(1f);
				image.draw(x() * Tile.SIZE - xOffset, y() * Tile.SIZE - yOffset, image.getWidth(), image.getHeight());
			}
		}

	}




}

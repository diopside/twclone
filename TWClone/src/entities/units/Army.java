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
		path = new ArrayList<>();

		t.setOccupyingEntity(this);
		initImage(dir);
		generateMaxMovement();
		remainingMovement = maxMovement;
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

	private void generateMaxMovement(){
		// This method will determine how fast the army should be based off of a number of factors

		maxMovement = 6;
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

	@Override 
	public ArrayList<String> getToolTip(){
		ArrayList<String> strings = new ArrayList<>();
		strings.add("");
		strings.add(name);
		strings.add("Army of " + owner.getName() + " faction.");

		return strings;
	}

	@Override
	public void move() {
		Tile t = path.get(0);

		if (t.occupied()){
			if (t.getOccupyingEntity() instanceof Army){
				if (!t.getOccupyingEntity().getOwner().equals(this.getOwner()))       ;
				// insert code for a battle here
			}
			else {
				path = PathGenerator.generateAStarPath(coord, path.get(path.size() - 1));
			}
		} // end case of path occupied
		else {
			coord = new Coordinates(t.getX(), t.getY());
			path.remove(0);
			remainingMovement --;
		}
	}

	public void processTurn(){
		if (path.size() > 0)
			while (remainingMovement > 0){
				move();
			}


	}




}





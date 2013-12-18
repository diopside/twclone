package entities.units;

import org.newdawn.slick.geom.Shape;

import entities.Coordinates;
import entities.Draggable;
import entities.Faction;
import entities.world.Territory;

public class Unit implements Draggable {

	protected Territory location;
	protected Coordinates coord;
	protected Faction owner;
	private boolean dragging;

	@Override
	public Shape getOffsetShape(int mouseX, int mouseY) {
		return null;
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
		// TODO Auto-generated method stub.
		
	}
	
}

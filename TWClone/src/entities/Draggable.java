package entities;

import org.newdawn.slick.geom.Shape;

public interface Draggable {
	
	public Shape getOffsetShape(int xOffset, int yOffset);
	public Coordinates getCoordinates();
	public void drag(Coordinates offCoords, int mouseX, int mouseY);
	public void setDragging(boolean b);
	
	
	
	
	

}

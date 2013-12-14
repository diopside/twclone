package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;

public class ColoredShape{

	private Shape shape;
	private Color color;
	
	public ColoredShape(Shape shape, Color color){
		this.shape = shape;
		this.color = color;
	}
	
	public Shape getShape(){
		return shape;
	}
	public Color getColor(){
		return color;
	}
	public void setColor(Color c){
		color = c;
	}

}



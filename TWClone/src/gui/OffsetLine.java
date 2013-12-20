package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;

import entities.Coordinates;

public class OffsetLine extends Line {
	
	private float x1, x2, y1, y2;
	

	public OffsetLine(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public Line getOffsetLine(int xOffset, int yOffset){
		return new Line(x1 - xOffset, y1 - yOffset, x2 -xOffset, y2 - yOffset);
	}
	
	
	public Coordinates getPoint1(){
		return new Coordinates(x1, y1);
	}
	public Coordinates getPoint2(){
		return new Coordinates(x2, y2);
	}
}

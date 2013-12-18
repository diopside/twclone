package entities;

public class Coordinates {
	
	
	private int x;
	private int y;
	
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Coordinates(){
		this(0, 0);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinates getOffsetCoordinates(int xOffset, int yOffset){
		return new Coordinates(x - xOffset, y - yOffset);
	}
	

}

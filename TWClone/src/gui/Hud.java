package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Hud {

	private static final int X = 0, Y = 600;
	private Image frame;
	private MiniMap miniMap;
	
	public Hud(){
		
		miniMap = new MiniMap();
		initImages();
	}
	
	private void initImages(){
		try {
			frame = new Image("res/hud/hud.png");
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
		frame.setAlpha(.9f);
	}
	
	
	public void render(Graphics g){
		frame.draw(X, Y);
		
	}
	
	
	
	
	public MiniMap getMiniMap(){
		return miniMap;
	}
	
	
	
}

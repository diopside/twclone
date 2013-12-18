package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import states.Game;
import entities.world.World;

public class Hud {

	private static final int X = 0, Y = 600;
	private Image frame;
	private MiniMap miniMap;
	private World world;
	
	public Hud(World world){
		this.world = world;
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
		renderInformation(g);
	}
	
	private void renderInformation(Graphics g){
		g.setFont(Game.MORRIS_ROMAN_18);
		g.setColor(Color.white);
		g.drawString("Year: "+world.getYear(), 25, Y + 75);
		g.drawString("Gold: "+ world.getPlayer().getGold(), 25, + Y + 90);
		g.drawString("Wood: "+ world.getPlayer().getWood(), 25, + Y + 105);
		g.drawString("Minerals: "+ world.getPlayer().getMinerals(), 25, + Y + 120);
	}
	
	
	
	
	public MiniMap getMiniMap(){
		return miniMap;
	}
	
	
	
}

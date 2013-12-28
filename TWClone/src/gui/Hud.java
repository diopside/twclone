package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import states.Game;
import entities.units.Unit;
import entities.world.World;

public class Hud {

	private static final int X = 0, Y = 600, END_TURN_X = Game.WIDTH - 144, END_TURN_Y = Game.HEIGHT - 64;
	private Image frame;
	private MiniMap miniMap;
	private World world;
	private Unit selectedUnit;
	private BasicButton endTurnBtn;
	
	public Hud(World world){
		this.world = world;
		miniMap = new MiniMap();
		initImages();
		
		endTurnBtn = new BasicButton(END_TURN_X, END_TURN_Y, "res/buttons/hud/endturn.png");
	}
	
	private void initImages(){
		try {
			frame = new Image("res/hud/hud.png");
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
		frame.setAlpha(.9f);
	}
	
	public boolean hasSelectedUnit(){
		return selectedUnit != null;
	}
	
	public void select(Unit u){
		selectedUnit = u;
	}
	
	
	public void render(Graphics g, int xOffset, int yOffset){
		frame.draw(X, Y);
		renderInformation(g);
		endTurnBtn.render(g, 1f);
	}
	
	private void renderInformation(Graphics g){
		g.setFont(Game.MORRIS_ROMAN_18);
		g.setColor(Color.white);
		g.drawString("Year: "+world.getYear(), 25, Y + 75);
		g.drawString("Gold: "+ world.getPlayer().getGold(), 25, + Y + 90);
		g.drawString("Wood: "+ world.getPlayer().getWood(), 25, + Y + 105);
		g.drawString("Minerals: "+ world.getPlayer().getMinerals(), 25, + Y + 120);
		
		if (selectedUnit != null){
			g.drawString(selectedUnit.getName(), 400 + X, 100 + Y);
			int tX = selectedUnit.getCoordinates().getX(); int tY = selectedUnit.getCoordinates().getY();
			g.drawString("Tile: " + tX + "," + tY, 400 + X, 115 + Y);
		}
	}
	
	public void checkButtons(int mouseX, int mouseY, StateBasedGame game){
		/*
		 * This method will be called if the mouse button is clicked and the mouse is in the HUD
		 */
		
		if (endTurnBtn.contains(mouseX, mouseY)){
			game.enterState(Game.END_TURN_ID);
		}
				
	}
	
	
	
	
	public MiniMap getMiniMap(){
		return miniMap;
	}
	
	
	
}

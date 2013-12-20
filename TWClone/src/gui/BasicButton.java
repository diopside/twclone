package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class BasicButton extends Button {

	public Image image;

	public BasicButton(int x, int y, String loc){
		this.x = x;
		this.y = y;
		initImage(loc);
	}


	private void initImage(String loc){
		try {
			image = new Image(loc);
		} catch (SlickException exception) {
			exception.printStackTrace();
		}
	}

	public boolean contains(int x, int y) {
		Rectangle r = new Rectangle(this.x, this.y, image.getWidth(), image.getHeight());
		return r.contains(x, y);
	}
	
	public boolean offsetContains(int mouseX, int mouseY, int xOffset, int yOffset){
		Rectangle r = new Rectangle(this.x - xOffset, this.y - yOffset, image.getWidth(), image.getHeight());
		return r.contains(mouseX, mouseY);
	}

	@Override
	public void render(Graphics g, int xOffset, int yOffset, float alpha) {
		
		image.setAlpha(alpha);
		image.draw(x - xOffset, y - yOffset);

	}



}

package states;

import gui.BasicButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {

	private final int ID;
	private BasicButton newGameBtn, quitBtn;
	
	public MainMenu(int id){
		ID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		newGameBtn = new BasicButton(200, 200, "res/buttons/mainmenu/newgamebtn.png");
		quitBtn = new BasicButton(Game.WIDTH - 200, 200, "res/buttons/mainmenu/quitgamebtn.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		g.setBackground(Color.lightGray);
		newGameBtn.render(g, 1f);
		quitBtn.render(g, 1f);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if (input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
			if (newGameBtn.contains(mouseX, mouseY))
				game.enterState(Game.WORLD_STATE_ID);
			if (quitBtn.contains(mouseX, mouseY))
				container.exit();
				
		}
		
	}

	@Override
	public int getID() {
		return ID;
	}

}

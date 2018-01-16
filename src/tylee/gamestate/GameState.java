package tylee.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tylee.gui.state.State;
import tylee.main.BuildGame;
import tylee.main.util.BGU;
import tylee.main.util.TimeKeeper;
import tylee.main.world.World;
import tylee.main.world.mob.Mob;

/**
 * Game-playing state
 * 
 * @author tyvanroy
 *
 */
public class GameState extends State implements KeyListener {
	private static final long serialVersionUID = 5243111350630000754L;
	public static final String STATE_ID = "game state";
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 800;
	public static final int X_FRAME_BUFFER = 200;
	public static final int Y_FRAME_BUFFER = 150;
	public static final int TICK_RATE = 60;
	public static final int FPS = 30;
	private TimeKeeper timer;
	private GameScreen screen;
	private World world;
	private InputState inputState;

	public GameState() {
		timer = new TimeKeeper(this, TICK_RATE, FPS);
		screen = new GameScreen(this, FRAME_WIDTH - X_FRAME_BUFFER, FRAME_HEIGHT - Y_FRAME_BUFFER);

		BGU.autoSize(this, FRAME_WIDTH, FRAME_HEIGHT);
		this.setBackground(Color.BLACK);
		this.add(screen);
		this.addKeyListener(this);

		screen.setBounds(X_FRAME_BUFFER / 2, Y_FRAME_BUFFER / 2, screen.WIDTH, screen.HEIGHT);

		inputState = new InputState();
	}

	@Override
	public void init(World world) {
		this.world = world;
		screen.initGraphics();
		timer.init();
		this.requestFocus();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
	}

	/**
	 * Logic line
	 */
	public void tick() {
		handleInput();

		for (Mob mob : world.getActiveSub().getMobs()) {
			mob.act();
		}
	}

	/**
	 * Render line
	 */
	public void render() {
		world.shiftCam(world.player());
		screen.render();
	}

	public void handleInput() {
		world.handleInput(inputState);
	}

	public String getID() {
		return STATE_ID;
	}

	public World getWorld() {
		return world;
	}

	public GameScreen getScreen() {
		return screen;
	}
	
	public void halt(){
		screen.halt();
		timer.halt();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_P) {
			this.halt();
			screen = null;
			BuildGame.builderInit(world);
		} else {
			inputState.send(keyCode, InputState.KEY_DOWN);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		inputState.send(keyCode, InputState.KEY_UP);
	}

}

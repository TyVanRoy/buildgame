package tylee.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import tylee.builder.Builder;
import tylee.gamestate.GameState;
import tylee.gui.state.MainMenu;
import tylee.gui.state.State;
import tylee.main.util.BGU;
import tylee.main.world.Subworld;
import tylee.main.world.World;

/**
 * Mother class
 * 
 * @author tyvanroy
 *
 */
public abstract class BuildGame {
	public static String status;
	private static State activeState;
	private static JFrame window;

	public static void init() throws IOException {
		BGU.init();

		status = "main menu";
		activeState = null;

		window = new JFrame("Build Game");
		window.setResizable(false);

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					BGU.endLog();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		changeState(new MainMenu(), null, true);
	}

	public static void builderInit(World world) {
		Builder builder = new Builder();
		changeState(builder, world);
	}

	public static void playInit(boolean newWorld) {
		if (newWorld) {
			GameState game = new GameState();
			World world = new World();
			Subworld main = BGU.buildSubworld(world, 64);

			world.init(game, main);

			changeState(game, world);
		}
	}

	private static void changeState(State newState, World world) {
		changeState(newState, world, false);
	}

	private static synchronized void changeState(State newState, World world, boolean firstState) {
		if (!firstState) {
			window.setVisible(false);

			window.remove(activeState);
			activeState = null;
			window = new JFrame("Build Game");
		}

		activeState = newState;
		status = activeState.getID();

		window.add(activeState);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		activeState.init(world);
	}

	/**
	 * Handles init exceptions
	 */
	public static void main(String[] args) {
		try {
			init();

			BGU.log("BGU Successfully Loaded poop.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

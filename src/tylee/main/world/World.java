package tylee.main.world;

import java.awt.Point;
import java.util.ArrayList;

import tylee.gamestate.GameState;
import tylee.gamestate.InputState;
import tylee.main.world.mob.Mob;
import tylee.main.world.mob.Player;

/**
 * The unique game object class. Each has its own "Main Map"
 * 
 * @author tyvanroy
 *
 */
public class World {
	private GameState game;
	private ArrayList<Subworld> subworlds;
	private Player player;
	private Subworld activeSub;
	private Camera camera;

	public World() {
		subworlds = new ArrayList<Subworld>();
		player = new Player(this, new Point(0, 0), 40);
		camera = new Camera();

	}

	public void init(GameState game, Subworld... subworlds) {
		this.game = game;
		this.activeSub = subworlds[0];
		
		for(int i = 0; i < subworlds.length; i++){
			this.subworlds.add(subworlds[i]);
		}
	}
	
	public void handleInput(InputState state){
		movePlayer(state);
	}

	/**
	 * Player not included in standard mob list
	 * 
	 * @return
	 */
	public Player player() {
		return player;
	}

	public void movePlayer(InputState inputState) {
		int[] states = inputState.get();
		Point p = player.getPosition();

		for (int i = 0; i < states.length; i++) {
			switch (i) {
			case 0:
				p.y -= states[i];
				break;
			case 1:
				p.x -= states[i];
				break;
			case 2:
				p.y += states[i];
				break;
			case 3:
				p.x += states[i];
			default:
				break;
			}
		}
	}

	/**
	 * Focuses camera on a mob.
	 * 
	 * @param mob
	 */
	public void shiftCam(Mob mob) {
		camera.focus(game.getScreen(), mob.getPosition());
	}

	public Camera camera() {
		return camera;
	}

	public Subworld getActiveSub() {
		return activeSub;
	}

	public ArrayList<Subworld> getSubworlds() {
		return subworlds;
	}

}

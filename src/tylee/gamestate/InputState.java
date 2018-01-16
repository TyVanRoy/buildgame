package tylee.gamestate;

import java.awt.event.KeyEvent;

/**
 * For storing the various states of input Need to implement acceleration/timer
 * support
 * 
 * @author tyvanroy
 *
 */
public class InputState {
	public static final int KEY_DOWN = 1;
	public static final int KEY_UP = 0;
	private int[] states;

	public InputState() {
		states = new int[4];
	}

	public int[] get() {
		return states;
	}

	public void send(int keyCode, int keyOp) {
		int stateID;
		switch (keyCode) {
		case KeyEvent.VK_W:
			stateID = 0;
			break;
		case KeyEvent.VK_A:
			stateID = 1;
			break;
		case KeyEvent.VK_S:
			stateID = 2;
			break;
		case KeyEvent.VK_D:
			stateID = 3;
			break;
		default:
			return;
		}

		switch (keyOp) {
		case KEY_DOWN:
			states[stateID] = 2;
			break;
		case KEY_UP:
			states[stateID] = 0;
			break;
		}
	}
}

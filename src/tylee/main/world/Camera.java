package tylee.main.world;

import java.awt.Point;

import tylee.gamestate.GameScreen;

/**
 * Add camera-cloning implementation
 * 
 * @author tyvanroy
 *
 */
public class Camera {
	public int x;
	public int y;

	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Camera() {
		this(0, 0);
	}

	/**
	 * Centers the camera on a position p
	 * 
	 * @param p
	 */
	public void focus(GameScreen screen, Point p) {
		int xshift = screen.WIDTH / 2;
		int yshift = screen.HEIGHT / 2;
		
		x = p.x - xshift;
		y = p.y - yshift;
	}

	public void reset() {
		x = 0;
		y = 0;
	}
}

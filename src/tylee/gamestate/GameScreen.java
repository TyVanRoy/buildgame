package tylee.gamestate;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tylee.main.util.BGU;
import tylee.main.world.Camera;
import tylee.main.world.Subworld;
import tylee.main.world.Tile;
import tylee.main.world.World;
import tylee.main.world.mob.Mob;
import tylee.main.world.mob.Player;

/**
 * Game-player display
 * 
 * @author tyvanroy
 *
 */
public class GameScreen extends Canvas {
	private static final long serialVersionUID = 924912526530579787L;
	private GameState game;
	public final int WIDTH, HEIGHT;
	private BufferStrategy buffer;

	private ArrayList<Tile> visTiles = new ArrayList<Tile>();
	private ArrayList<Point> tilePoints = new ArrayList<Point>();
	private Camera cCam;
	private boolean showVision = false;;
	private int padding = 0;
	private boolean rendering;

	public GameScreen(GameState game, int width, int height) {
		this.game = game;
		this.WIDTH = width;
		this.HEIGHT = height;
		BGU.autoSize(this, WIDTH, HEIGHT);
	}

	public void initGraphics() {
		cCam = new Camera();
		calculateVisibleTiles(padding, true);
		if (buffer == null) {
			createBufferStrategy(2);
			buffer = getBufferStrategy();
			rendering = true;
		}
	}

	/**
	 * Render line
	 */
	public void render() {
		if (rendering) {
			Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.setColor(Color.magenta);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			if (calculateVisibleTiles(padding, false)) {
			}

			dropTiles(g);
			dropMobs(g, showVision);

			dropPlayer(g);

			g.dispose();
			buffer.show();
			Toolkit.getDefaultToolkit().sync();
		}
	}

	public synchronized void resume() {
		rendering = true;
	}

	public synchronized void halt() {
		rendering = false;
	}

	/**
	 * Drops each mob from the active subworld.
	 * 
	 * Needs reduction in mobs drawn.
	 * 
	 * @param g
	 */
	private void dropMobs(Graphics2D g, boolean showVision) {
		World w = game.getWorld();
		Subworld sw = w.getActiveSub();
		ArrayList<Mob> mobs = sw.getMobs();

		for (Mob mob : mobs) {
			dropMob(g, mob, showVision);
		}
	}

	/**
	 * Draws a mob onto g.
	 * 
	 * @param g
	 * @param mob
	 */
	private void dropMob(Graphics2D g, Mob mob, boolean showVision) {
		BufferedImage img = mob.restIMG();

		Point drawPos = mob.getDrawPosition();
		int x = drawPos.x;
		int y = drawPos.y;

		dropIMG(img, x, y, g);

		if (showVision) {

			drawPos = mob.getPosition();
			int vis = mob.getVision();
			x = drawPos.x - vis;
			y = drawPos.y - vis;

			g.setColor(Color.blue);
			g.drawOval(x - game.getWorld().camera().x, y - game.getWorld().camera().y, vis * 2, vis * 2);
		}
	}

	/**
	 * Drops the player onto g
	 * 
	 * @param g
	 */
	private void dropPlayer(Graphics2D g) {
		Player player = game.getWorld().player();

		dropMob(g, player, false);
	}

	/**
	 * Drops visible tiles onto g. Uses local ArrayLists, visTiles & tilePoints.
	 * 
	 * Maybe objectify further.
	 * 
	 * @param g
	 *            graphics object taking the tiles
	 */
	private void dropTiles(Graphics2D g) {
		int x, y;
		for (int i = 0; i < visTiles.size(); i++) {
			Point p = tilePoints.get(i);
			x = (int) p.getX();
			y = (int) p.getY();
			g.drawImage(visTiles.get(i).img(), x, y, null);
		}
	}

	private void dropIMG(BufferedImage img, int x, int y, Graphics2D g) {
		int nx = x - game.getWorld().camera().x;
		int ny = y - game.getWorld().camera().y;

		g.drawImage(img, nx, ny, null);
	}

	/**
	 * Calculates visible tiles from the active subworld.
	 * 
	 * Inefficient. Needs re-working.
	 * 
	 * Instead of checking each tile for viable params, calculate viable tile
	 * sample based on params.
	 * 
	 * Also, add padding implementation.
	 */
	private boolean calculateVisibleTiles(int padding, boolean firstFrame) {
		boolean did = false;
		World w = game.getWorld();
		Camera newCam = w.camera();

		if (cCam.x != newCam.x || cCam.y != newCam.y || firstFrame) {
			did = true;
			Subworld sw = w.getActiveSub();
			cCam = new Camera(newCam.x, newCam.y);
			int subSize = sw.tileSize();

			ArrayList<Tile> allTiles = sw.getTiles();
			tilePoints.clear();

			visTiles.clear();

			// int trueSubSize = subSize * Tile.SIZE;
			int tx, ty, px, py;

			for (int i = 0; i < allTiles.size(); i++) {
				tx = i % (subSize) * Tile.SIZE;
				ty = i / (subSize) * Tile.SIZE;

				if (tx >= cCam.x - Tile.SIZE + padding && ty >= cCam.y - Tile.SIZE + padding) {
					if (tx <= (cCam.x + WIDTH - padding) && ty <= (cCam.y + HEIGHT - padding)) {
						Tile tile = allTiles.get(i);
						visTiles.add(tile);

						px = tx - cCam.x;
						py = ty - cCam.y;

						tilePoints.add(new Point(px, py));
					}
				}
			}
		}

		return did;
	}
}

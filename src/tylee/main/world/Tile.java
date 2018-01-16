package tylee.main.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tylee.main.util.BGU;

/**
 * Basic physical subworld unit
 * 
 * @author tyvanroy
 *
 */
public class Tile {
	public static final int SIZE = 24;
	public String tag;
	private BufferedImage restIMG;
	private boolean walkable;

	public Tile(BufferedImage img) {
		walkable = true;
		tag = "custom";
		restIMG = img;
	}

	public Tile(String tag) {
		this(BGU.getTileIMG(tag));
		this.tag = tag;
	}

	public Tile() {
		this("empty");
	}

	/**
	 * For testing
	 * 
	 * @param i
	 */
	public Tile(int i) {
		this();

		restIMG = new BufferedImage(Tile.SIZE, Tile.SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = restIMG.getGraphics();
		g.setColor(new Color(i % 255, 0, 0));
		g.fillRect(0, 0, Tile.SIZE, Tile.SIZE);
	}

	public void setIMG(BufferedImage img) {
		this.restIMG = img;
	}

	public BufferedImage img() {
		return restIMG;
	}

	public boolean walkable() {
		return walkable;
	}

}

package tylee.main.world.mob;

import java.awt.Point;
import java.awt.image.BufferedImage;

import tylee.main.util.BGU;
import tylee.main.world.Subworld;

/**
 * Needs to be merged with Tile.
 * 
 * Possibly "imageable" object
 * 
 * @author tyvanroy
 *
 */
public abstract class Mob {
	protected int size;
	protected BufferedImage restIMG;
	protected String tag;
	protected Subworld subworld;
	protected Point position;

	protected abstract void setTag();
	
	protected abstract void setSubworld(Subworld subworld);

	protected abstract void setPosition(Point position);
	
	public boolean act(){
		return false;
	}

	protected void setRestIMG() {
		this.restIMG = BGU.getMobIMG(tag);
	}
	
	protected Subworld getSubworld(){
		return subworld;
	}

	/**
	 * Returns position which lies in the center of the mob.
	 * 
	 * @return
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Returns a position which is adjusted in order to draw the image.
	 * 
	 * x - imageX / 2; y - imageY / 2;
	 * 
	 * @return
	 */
	public Point getDrawPosition() {
		int x = position.x - (size / 2);
		int y = position.y - (size / 2);

		return new Point(x, y);
	}

	public int size() {
		return size;
	}

	public BufferedImage restIMG() {
		return restIMG;
	}
	
	public String tag(){
		return tag;
	}
	
	public int getVision(){
		return 0;
	}
}

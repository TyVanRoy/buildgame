package tylee.main.world.mob;

import java.awt.Point;

import tylee.main.world.Subworld;
import tylee.main.world.World;

/**
 * Player mob
 * 
 * @author tyvanroy
 *
 */
public class Player extends Mob {
	private World world;

	public Player(World world, Point position, int size) {
		this.world = world;
		this.size = size;
		setPosition(position);
		setTag();
		setRestIMG();
	}

	@Override
	protected Subworld getSubworld() {
		return world.getActiveSub();
	}

	@Override
	protected void setPosition(Point position) {
		this.position = position;
	}

	@Override
	protected void setTag() {
		this.tag = "player";
	}

	@Override
	protected void setSubworld(Subworld subworld) {
	}

}

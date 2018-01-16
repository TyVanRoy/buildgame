package tylee.main.world.mob;

import java.awt.Point;

import tylee.main.world.Subworld;
import tylee.main.world.Tile;

public class Grunt extends Mob {
	public static final int FULL_MOVEMENT = Tile.SIZE * 2;
	public static final int VISION = Tile.SIZE * 10;

	public Grunt(Subworld subworld, Point position) {
		this.size = 60;
		setTag();
		setSubworld(subworld);
		setPosition(position);
		setRestIMG();
	}

	/**
	 * Determines if this Grunt can see the target mob based on a radius
	 * Grunt.VISION.
	 * 
	 * Uses a circular field of vision.
	 * 
	 * @param mob
	 * @return if the given mob is in visible range.
	 */
	public boolean canSee(Mob mob) {
		int ax = position.x;
		int ay = position.y;
		int bx = mob.position.x;
		int by = mob.position.y;
		
		int radius = VISION;
		
		double dis = Math.sqrt((Math.pow(ax - bx, 2)) + (Math.pow(ay - by, 2)));

		if (dis < radius)
			return true;

		return false;
	}

	public boolean act() {
		return act(.5, .05);
	}

	/**
	 * Moves the grunt towards the player if it can see it.
	 * 
	 * @param actChance
	 *            in [0, 1).
	 * @param intensity
	 *            in [0, 1).
	 * @return true if acted, false if did nothing. False if invalid params.
	 */
	public boolean act(double actChance, double intensity) {
		if ((actChance >= 0 && actChance < 1) && (intensity >= 0 && intensity < 1)) {

			if (this.canSee(subworld.world().player())) {
				if (actChance > Math.random()) {
					Point oldPos = this.position;
					Point playerPos = subworld.world().player().position;

					double newX = oldPos.getX();
					double newY = oldPos.getY();
					double tarX = playerPos.getX();
					double tarY = playerPos.getY();
					double moveX = intensity * FULL_MOVEMENT;
					double moveY = intensity * FULL_MOVEMENT;

					if (tarX < newX)
						moveX *= -1;
					if (tarY < newY)
						moveY *= -1;

					newX += moveX;
					newY += moveY;
					this.position.setLocation((int) newX, (int) newY);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	protected void setPosition(Point position) {
		this.position = position;
	}

	@Override
	protected void setTag() {
		tag = "grunt";
	}

	@Override
	protected void setSubworld(Subworld subworld) {
		this.subworld = subworld;
	}
	
	@Override
	public int getVision(){
		return VISION;
	}

}

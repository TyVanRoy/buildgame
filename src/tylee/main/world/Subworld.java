package tylee.main.world;

import java.util.ArrayList;

import tylee.main.world.mob.Mob;

/**
 * The map object. Named subworld because of "map" overuse.
 * 
 * @author tyvanroy
 *
 */
public class Subworld {
	private final int TILE_SIZE;
	private World world;
	private String name;
	private ArrayList<Tile> tiles;
	private ArrayList<Mob> mobs;

	/**
	 * For JList purposes
	 */
	public String toString() {
		return name;
	}

	public Subworld(World world, int tileSize, String name) {
		this.world = world;
		this.name = name;
		TILE_SIZE = tileSize;
		tiles = new ArrayList<Tile>();
		mobs = new ArrayList<Mob>();
	}

	/**
	 * Just in-case.
	 * 
	 * @param tiles
	 */
	public void init(ArrayList<Tile> tiles, ArrayList<Mob> mobs) {
		this.tiles = tiles;
		this.mobs = mobs;
	}
	
	public World world(){
		return world;
	}

	public String name() {
		return name;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Mob> getMobs() {
		return mobs;
	}

	/**
	 * Possibly needs synchronization.
	 * 
	 * @param newTiles
	 * @return
	 */
	public boolean swapTiles(ArrayList<Tile> newTiles) {
		if (newTiles.size() == TILE_SIZE) {
			tiles.clear();
			tiles = newTiles;
			return true;
		}

		return false;
	}

	public int tileSize() {
		return TILE_SIZE;
	}

	public int pixelSize() {
		return TILE_SIZE * Tile.SIZE;
	}

}

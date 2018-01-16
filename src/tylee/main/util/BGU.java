package tylee.main.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import tylee.main.world.Subworld;
import tylee.main.world.Tile;
import tylee.main.world.World;
import tylee.main.world.mob.Grunt;
import tylee.main.world.mob.Mob;

/**
 * Build Game Utilities
 * 
 * Possible need to be split up eventually Currently houses all utility-like
 * function
 * 
 * @author tyvanroy
 *
 */
public abstract class BGU {
	private static final String S = System.getProperty("os.name").equals("Mac OS X") ? "//" : "\\";
	private static final String RES = "res";
	private static final String TILES = "tile";
	private static final String MOBS = "mob";
	private static final String IMG = "img";
	private static ArrayList<BufferedImage> tileImages;
	private static ArrayList<String> tileIDs;
	private static ArrayList<BufferedImage> mobImages;
	private static ArrayList<String> mobIDs;
	private static String log;

	/**
	 * Needs to be called before almost all BuildGame activity
	 * 
	 * ID system is bad.
	 * 
	 * @throws IOException
	 */
	public static void init() throws IOException {
		tileIDs = new ArrayList<String>();
		tileImages = new ArrayList<BufferedImage>();
		loadIDs(tileIDs, buildPath(TILES, "ids.txt"));
		loadImages(tileImages, tileIDs, TILES);

		mobIDs = new ArrayList<String>();
		mobImages = new ArrayList<BufferedImage>();
		loadIDs(mobIDs, buildPath(MOBS, "ids.txt"));
		loadImages(mobImages, mobIDs, MOBS);

		log = "";
	}

	/**
	 * Logs entries into log.txt located in "res"/ Log ends on close.
	 * 
	 * @param entry
	 *            added to the log file
	 * @return
	 */
	public static boolean log(String entry) {
		boolean success = true;

		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			entry = time + ":\t" + entry;
		} catch (Error e) {
			entry = "\t*** Failed to write entry to log";
			success = false;
		}

		log += entry;

		return success;
	}

	public static void endLog() throws IOException {
		log += "\n\t\t-----end log-----\n\n\n";

		BufferedWriter writer = new BufferedWriter(new FileWriter(buildPath("log.txt"), true));
		writer.append(log);
		writer.close();
	}

	/**
	 * Temporary method for building sample-worlds.
	 * 
	 * @param size
	 * @return
	 */
	public static Subworld buildSubworld(World world, int size) {
		Subworld main = new Subworld(world, size, "main map");
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		ArrayList<Mob> mobs = new ArrayList<Mob>();

		for (int i = 0; i < (size * size); i++) {
			String id = Math.random() > .7 ? "dung_floor_cracked" : "dung_floor";
			tiles.add(new Tile(id));
		}
		
		Mob m = new Grunt(main, new Point(20 * Tile.SIZE, 20 * Tile.SIZE));
		
		mobs.add(m);
		main.init(tiles, mobs);
		return main;
	}

	public static BufferedImage getTileIMG(String id) {
		for (int i = 0; i < tileIDs.size(); i++) {
			if (tileIDs.get(i).equals(id)) {
				return tileImages.get(i);
			}
		}

		return null;
	}

	public static BufferedImage getMobIMG(String id) {
		for (int i = 0; i < mobIDs.size(); i++) {
			if (mobIDs.get(i).equals(id))
				return mobImages.get(i);
		}

		return null;
	}

	/**
	 * Resizes component automatically.
	 * 
	 * Needs flexibility implementation.
	 * 
	 * @param c
	 * @param width
	 * @param height
	 */
	public static void autoSize(Component c, int width, int height) {
		Dimension d = new Dimension(width, height);
		c.setPreferredSize(d);
		c.setMinimumSize(d);
		c.setMaximumSize(d);
	}

	/**
	 * Loads image ids
	 * 
	 * @param ids
	 * @param path
	 * @throws FileNotFoundException
	 */
	private static void loadIDs(ArrayList<String> ids, String path) throws FileNotFoundException {
		Scanner in = new Scanner(new File(path));
		while (in.hasNext()) {
			ids.add(in.nextLine());
		}
		in.close();
	}

	private static void loadImages(ArrayList<BufferedImage> images, ArrayList<String> ids, String folder)
			throws IOException {
		String id = null;
		for (int i = 0; i < ids.size(); i++) {
			id = ids.get(i);
			images.add(loadIMG(folder, IMG, id + ".png"));
		}

	}

	private static BufferedImage loadIMG(String... folders) throws IOException {
		return ImageIO.read(new File(buildPath(folders)));
	}

	private static String buildPath(String... folders) throws IOException {
		String path = RES;
		for (int i = 0; i < folders.length; i++) {
			path += S;
			path += folders[i];
		}
		return path;
	}
}

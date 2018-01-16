package tylee.builder;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tylee.main.util.BGU;
import tylee.main.world.Subworld;
import tylee.main.world.Tile;
import tylee.main.world.mob.Mob;

/**
 * Main display for the game-builder
 * 
 * @author tyvanroy
 *
 */
public class Stage extends Canvas {
	private static final long serialVersionUID = -5291295270303439280L;
	Builder builder;
	private int width, height;
	private boolean showVision = true;

	public Stage(Builder builder, int width, int height) {
		this.builder = builder;
		this.width = width;
		this.height = height;

		BGU.autoSize(this, width, height);
	}

	/**
	 * Just terrible
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, width, height);
		
		Subworld s = builder.getActiveSub();
		ArrayList<Tile> tiles = s.getTiles();
		int size = s.tileSize();
		BufferedImage img;
		int x;
		int y;
		
		for(int i = 0; i < tiles.size(); i++){
			Tile t = tiles.get(i);
			img = t.img();
			
			x = Tile.SIZE * (i % size);
			y = Tile.SIZE * (i / size);
			
			g.drawImage(img, x, y, null);
		}
		
		ArrayList<Mob> mobs = s.getMobs();
		Point pos;
		int vis;
		
		for(int i = 0; i < mobs.size(); i++){
			Mob m = mobs.get(i);
			img = m.restIMG();
			
			x = m.getDrawPosition().x;
			y = m.getDrawPosition().y;
			
			pos = m.getDrawPosition();
			
			g.drawImage(img, x, y, null);
			
			if (showVision) {

				pos = m.getPosition();
				vis = m.getVision();
				x = pos.x - vis;
				y = pos.y - vis;

				g.setColor(Color.blue);
				g.drawOval(x, y, vis * 2, vis * 2);
			}
		}
	}

	public Dimension size() {
		return new Dimension(width, height);
	}

}

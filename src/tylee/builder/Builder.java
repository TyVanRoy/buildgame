package tylee.builder;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;

import tylee.gui.state.State;
import tylee.main.util.BGU;
import tylee.main.world.Subworld;
import tylee.main.world.World;

/**
 * Game-builder State
 * 
 * @author tyvanroy
 *
 */
public class Builder extends State {
	private static final long serialVersionUID = 1938219770161069755L;
	public static final int DEF_WIDTH = 1000;
	public static final int DEF_HEIGHT = 800;
	public static final String STATE_ID = "builder";
	private Stage stage;
	private JPanel subWorldListPanel;
	private JPanel editorPanel;
	private JList<Object> subworldLister;
	private World world;
	private Subworld activeSubworld;

	public Builder(boolean newGame) {
		BGU.autoSize(this, DEF_WIDTH, DEF_HEIGHT);
	}

	public Builder() {
		this(true);
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public Subworld getActiveSub(){
		return activeSubworld;
	}

	@Override
	public String getID() {
		return STATE_ID;
	}

	@Override
	public void init(World world) {
		
		if (world != null) {
			this.world = world;
		} else {
			world = new World();
			Subworld sub = BGU.buildSubworld(world, 40);
			world.init(null, sub);
			this.world = world;
		}
		
		
		stage = new Stage(this, (int) (DEF_WIDTH * (2.0 / 3.0)), (int) (DEF_HEIGHT * (2.0 / 3.0)));
		subWorldListPanel = new JPanel();
		editorPanel = new JPanel();
		subworldLister = new JList<Object>(world.getSubworlds().toArray());

		BGU.autoSize(subWorldListPanel, (int) (DEF_WIDTH * (1.0 / 3.0)), (int) (DEF_HEIGHT * (2.0 / 3.0)));
		BGU.autoSize(editorPanel, DEF_WIDTH, (int) (DEF_HEIGHT * (1.0 / 5.0)));
		BGU.autoSize(subworldLister, subWorldListPanel.getPreferredSize().width,
				subWorldListPanel.getPreferredSize().height);
		
		editorPanel.setBackground(Color.white);
		editorPanel.setBorder(BorderFactory.createTitledBorder("Editor"));

		subWorldListPanel.setLayout(new BorderLayout());
		subWorldListPanel.add(subworldLister, BorderLayout.CENTER);
		
		subworldLister.setBackground(Color.lightGray);

		this.setLayout(new BorderLayout());
		this.setBackground(Color.DARK_GRAY);

		this.add(subWorldListPanel, BorderLayout.WEST);
		this.add(stage, BorderLayout.EAST);
		this.add(editorPanel, BorderLayout.SOUTH);
		
		this.activeSubworld = world.getSubworlds().get(0);
	}
}

package tylee.gui.state;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import tylee.main.BuildGame;
import tylee.main.util.BGU;
import tylee.main.world.World;

/**
 * Main menu state
 * 
 * @author tyvanroy
 *
 */
public class MainMenu extends State implements ActionListener {
	private static final long serialVersionUID = 1303151422118303259L;
	public static final int MENU_WIDTH = 600;
	public static final int MENU_HEIGHT = 400;
	public static final String STATE_ID = "main menu";

	private JButton buildButton;
	private JButton playButton;

	public MainMenu() {
		BGU.autoSize(this, MENU_WIDTH, MENU_HEIGHT);

		initBuildButton();
		initPlayButton();

		formatLayout();
	}

	private void formatLayout() {
		this.setLayout(new BorderLayout());
		this.add(this.buildButton, BorderLayout.WEST);
		this.add(this.playButton, BorderLayout.EAST);
	}

	private void initBuildButton() {
		buildButton = new JButton("Build");
		buildButton.setForeground(Color.magenta);
		BGU.autoSize(buildButton, MENU_WIDTH / 2, MENU_HEIGHT);
		buildButton.addActionListener(this);
	}

	private void initPlayButton() {
		playButton = new JButton("Play");
		playButton.setForeground(Color.green);
		BGU.autoSize(playButton, MENU_WIDTH / 2, MENU_HEIGHT);
		playButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == buildButton) {
			BuildGame.builderInit(null);
		} else if (source == playButton) {
			BuildGame.playInit(true);
		}
	}

	@Override
	public String getID() {
		return STATE_ID;
	}

	@Override
	public void init(World world) {
	}

}

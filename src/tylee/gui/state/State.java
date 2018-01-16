package tylee.gui.state;

import javax.swing.JComponent;

import tylee.main.world.World;

/**
 * State Object
 * 
 * State of activities
 * 
 * @author tyvanroy
 *
 */
public abstract class State extends JComponent {
	private static final long serialVersionUID = 7992371254385953716L;

	public abstract String getID();

	public abstract void init(World world);
}

package mpp.library.view;

import java.util.HashMap;

/**
 * @author Toan Quach
 *
 */
public interface ControlledScreen {

	/**
	 * This hash map will contains all the controller of all screen
	 */
	HashMap<Screen, ControlledScreen> controllerList = new HashMap<Screen, ControlledScreen>();

	/**
	 * This method will allow the injection of the Parent ScreenPane
	 * 
	 * @param screenPage
	 *            ScreenController
	 */
	void setScreenParent(ScreenController screenPage);

	/**
	 * This method is to repaint the layout of the screen when we back or click
	 * another button from other pages
	 */
	void repaint();
}

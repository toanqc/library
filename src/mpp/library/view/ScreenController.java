package mpp.library.view;

import java.io.IOException;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Toan Quach
 */
public class ScreenController extends StackPane implements ControlledScreen {

	private ScreenController myController;
	private Stage primaryStage;
	private HashMap<Screen, Node> screens = new HashMap<>();

	public void setScreenParent(ScreenController screenParent) {
		myController = screenParent;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setSize(int width, int height) {
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
	}

	@FXML
	private void goToMain(ActionEvent event) {
		myController.setScreen(Screen.HOME);
	}

	public void addScreen(Screen screenName, Node screen) {
		screens.put(screenName, screen);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean setScreen(final Screen name) {
		if (screens.get(name) != null) { // screen loaded
			final DoubleProperty opacity = opacityProperty();
			// Is there is more than one screen
			if (!getChildren().isEmpty()) {
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(0.1), new EventHandler() {
							@Override
							public void handle(Event t) {
								faceInNewScreen(name, opacity);
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();
			} else {
				firstScene(name, opacity);
			}
			return true;
		} else {
			return false;
		}
	}

	private void faceInNewScreen(final Screen name, final DoubleProperty opacity) {
		// remove displayed screen
		getChildren().remove(0);
		// add new screen
		getChildren().add(0, screens.get(name));
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(1500), new KeyValue(opacity, 1.0)));
		fadeIn.play();
	}

	private void firstScene(final Screen name, final DoubleProperty opacity) {
		// no one else been displayed, then just show
		setOpacity(0.0);
		getChildren().add(screens.get(name));
		Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(1500), new KeyValue(opacity, 1.0)));
		fadeIn.play();
	}

	public boolean unloadScreen(String name) {
		if (screens.remove(name) == null) {
			System.out.println("Screen didn't exist");
			return false;
		} else {
			return true;
		}
	}

	public boolean loadScreen(Screen screenName, String resource) {
		try {
			if (!screens.containsKey(screenName)) {
				FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
				Parent loadScreen = (Parent) myLoader.load();
				ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
				ControlledScreen.controllerList.put(screenName, myLoader.getController());
				myScreenControler.setScreenParent(this);
				addScreen(screenName, loadScreen);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	@Override
	public void repaint() {
		throw new UnsupportedOperationException("Repaint method not needed for screeen controller class");
	}
}
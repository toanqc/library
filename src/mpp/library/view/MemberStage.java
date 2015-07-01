package mpp.library.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemberStage extends Stage {

	public MemberStage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("member.fxml"));
		this.setTitle("Member");
		this.setScene(new Scene(root, 550, 400));
		this.setResizable(false);
		this.show();
	}
}

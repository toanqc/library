package mpp.library.view.member;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemberListStage extends Stage {

	public MemberListStage() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("memberList.fxml"));
		this.setTitle("Member Management");
		this.setScene(new Scene(root, 800, 600));
		this.setResizable(false);
		this.show();
	}
}

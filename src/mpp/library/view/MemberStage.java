package mpp.library.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpp.library.controller.FunctionType;
import mpp.library.controller.MemberController;

public class MemberStage extends Stage {

	public MemberStage(FunctionType functionType, int memberId) {
		FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(getClass().getResource("member.fxml"));
			Parent root = (Parent) loader.load();
			MemberController controller = loader
					.<MemberController> getController();
			controller.transferData(functionType, memberId);
			this.setTitle("Member");
			this.setScene(new Scene(root, 550, 400));
			this.setResizable(false);
			this.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

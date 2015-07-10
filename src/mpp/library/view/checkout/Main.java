package mpp.library.view.checkout;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpp.library.model.Address;
import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.Periodical;
import mpp.library.model.dao.impl.CheckoutDAOFacade;
import mpp.library.model.dao.impl.CopyDAOFacade;
import mpp.library.model.dao.impl.PublicationDAOFacade;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("Checkout.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("PrintCheckoutRecord.fxml"));
		primaryStage.setTitle("Checkout");
		primaryStage.setScene(new Scene(root, 500, 350));
		initData();
		primaryStage.show();
		primaryStage.setResizable(false);
		
	}

	public static void main(String[] args) {
		
		launch(args);
	}
	
	public void initData() {
		LibraryMember member = new LibraryMember(12, "Bao", "Pham", "12345", new Address("3th", "FF", "IA", 52557));
		CheckoutDAOFacade ckDAO = new CheckoutDAOFacade();
		ckDAO.save(member);
		initCopy(member);
	}
	
	public void initCopy(LibraryMember member) {
		Book book = new Book("ISBN1");
		book.setMaxCheckoutLength(21);
		Periodical periodical = new Periodical("New York times", "PERIOD01");
		periodical.setMaxCheckoutLength(7);
		Copy copy1 = new Copy(book, 1, true);
		Copy copy2 = new Copy(book, 2, true);
		
		
		List<Copy> listCopyBook = new ArrayList<Copy>();
		listCopyBook.add(copy1);
		listCopyBook.add(copy2);
		
		book.setCopies(listCopyBook);
		
		Copy copy3 = new Copy(periodical, 3, true);
		Copy copy4 = new Copy(periodical, 4, true);
		
		List<Copy> listCopyPeriod = new ArrayList<Copy>();
		listCopyPeriod.add(copy3);
		listCopyPeriod.add(copy4);
		
		periodical.setCopies(listCopyPeriod);
		
		CopyDAOFacade copyDAOFacade = new CopyDAOFacade();
		copyDAOFacade.save(copy1);
		copyDAOFacade.save(copy2);
		copyDAOFacade.save(copy3);
		copyDAOFacade.save(copy4);
		
		PublicationDAOFacade pubDAOFacade = new PublicationDAOFacade();
		pubDAOFacade.save(book);
		pubDAOFacade.save(periodical);
	}
}

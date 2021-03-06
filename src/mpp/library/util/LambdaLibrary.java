package mpp.library.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import mpp.library.model.Book;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.Copy;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.PublicationType;

/**
 * @author Anil
 *
 */
public class LambdaLibrary {

	public static final BiFunction<List<CheckoutRecordEntry>, LibraryMember, List<PublicationOverdueRecord>> PUBLICATION_OVERDUE_RECORD_LAMBDA = (
			crEntries,
			libraryMem) -> crEntries.stream().filter(cr -> cr.getDueDate().compareTo(LocalDate.now()) < 0).map((cr) -> {
				PublicationOverdueRecord publicationOverdueRecord = new PublicationOverdueRecord();
				Publication publication = cr.getCopy().getPublication();

				if (publication instanceof Book) {
					publicationOverdueRecord.setIssueNo(((Book) publication).getISBN());
					publicationOverdueRecord.setType(PublicationType.BOOK.getValue());
				} else {
					publicationOverdueRecord.setIssueNo(((Periodical) publication).getIssueNumber());
					publicationOverdueRecord.setType(PublicationType.PERIODICAL.getValue());
				}

				publicationOverdueRecord.setChkoutDate(cr.getCheckoutDate());
				publicationOverdueRecord.setDueDate(cr.getDueDate());
				publicationOverdueRecord.setMember(libraryMem.getFirstName() + " " + libraryMem.getLastName());
				return publicationOverdueRecord;
			}).collect(Collectors.toList());

	public static final Consumer<List<MemberCheckoutRecord>> PRINT_CHECKOUT_RECORD = s -> s.stream()
			.forEach(e -> System.out.println(e));

	public static final BiPredicate<String, String> REGEX_MATCHER = (input, regex) -> input.matches(regex) ? true
			: false;

	public static final Consumer<Pane> PANE_TEXTFIELD_CLEANER = pane -> {
		ObservableList<Node> nodes = pane.getChildren();
		nodes.stream().filter(node -> node instanceof TextField).forEach(node -> {
			TextField textField = (TextField) node;
			textField.clear();
		});
	};

	public static final BiFunction<Integer, Publication, List<Copy>> GENERATE_LIST_OF_COPIES = (copyNumber, pub) -> {
		List<Copy> copyList = new ArrayList<>();
		for (int i = 0; i < copyNumber; i++) {
			Copy copy = new Copy(pub, i, true);
			copyList.add(copy);
		}
		return copyList;
	};

	public static final BiFunction<List<PublicationOverdueRecord>, String, List<PublicationOverdueRecord>> FILTER_PUBLICATION_OVERDUE_RECORD = (
			publicationOverdueRecords, isbnIssueNumber) -> publicationOverdueRecords.stream()
					.filter(publicationOverdue -> publicationOverdue.getIssueno().matches(isbnIssueNumber.trim()))
					.collect(Collectors.toList());

}

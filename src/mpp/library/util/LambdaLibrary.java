package mpp.library.util;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import mpp.library.model.Book;
import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.LibraryMember;
import mpp.library.model.MemberCheckoutRecord;
import mpp.library.model.Periodical;
import mpp.library.model.Publication;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.PublicationType;

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

}

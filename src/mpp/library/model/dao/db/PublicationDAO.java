package mpp.library.model.dao.db;

import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.Publication;
import mpp.library.model.dao.BaseDAO;
import mpp.library.util.PublicationType;

public interface PublicationDAO extends BaseDAO<Publication> {

	/**
	 * Gets {@link Publication} from DB.
	 * 
	 * @param publicationType
	 *            {@link PublicationType}
	 * @param title
	 *            can be null or {@link String}
	 * @param isbnOrIssuenum
	 *            isbn if {@link PublicationType} is Book else issue_number
	 * @return
	 */
	Publication get(PublicationType publicationType, String title, String isbnOrIssuenum);

	List<Book> getBooksByAuthorId(int authorId);

}

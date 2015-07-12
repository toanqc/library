package mpp.library.model.service;

import mpp.library.model.Book;

/**
 * 
 * @author Anil
 *
 */
public interface BookService {

	/**
	 * Gets {@link Book} with provided ISBN number.
	 * 
	 * @param isbn
	 * @return {@link Book}
	 */
	Book getBook(String isbn);

	/**
	 * Saves Book in the DB.
	 * 
	 * @param book
	 * @param copiesNum
	 *            number of copies
	 */
	void saveBook(Book book, int copiesNum);

	boolean addCopy(String isbn, int copyNumber);

}

package mpp.library.model.service;

import mpp.library.model.Book;
import mpp.library.model.Copy;

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
	 */
	void saveBook(Book book);

	/**
	 * Adds {@link Copy} of the provided {@link Book} with provided copyNumber.
	 * 
	 * @param book
	 * @param copyNumber
	 */
	void addCopy(Book book, int copyNumber);

	/**
	 * Simply updates {@link Book} in the DB. Does nothing related to
	 * {@link Copy}.
	 * 
	 * @param book
	 */
	void updateBook(Book book);

	/**
	 * Updates {@link Copy} inside the {@link Book}.
	 * 
	 * @param book
	 * @param copy
	 */
	void updateBookCopy(Book book, Copy copy);
}

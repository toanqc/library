package mpp.library.model.service;

import mpp.library.model.Book;

/**
 * 
 * @author Anil
 *
 */
public interface BookService {

	Book getBook(String isbn);

	void saveBook(Book book);
	
	void addCopy(Book book, int copyNumber);

	void updateBook(Book book);
}

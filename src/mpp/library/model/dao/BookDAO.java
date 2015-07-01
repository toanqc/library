package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Book;

public interface BookDAO extends BaseDAO<Book> {

	void addCopy(Book book, int copyNumber);

	Book get(String isbn);

	Book save();

	List<Book> getBookList();

	boolean isAvailable();

}

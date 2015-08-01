package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Book;

public interface BookDAO extends BaseDAO<Book> {

	List<Book> getBookList();

	boolean isAvailable();

	void update(Book book);
}

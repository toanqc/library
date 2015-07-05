package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Book;

public interface BookDAO extends BaseDAO<Book> {


	Book get(String isbn);

	Book save();

	List<Book> getBookList();

	boolean isAvailable();

	void update(Book book);

}

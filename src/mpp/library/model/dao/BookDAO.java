package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Book;

public interface BookDAO {

	Book addCopy();

	Book get();

	Book save();

	List<Book> getBookList();

	boolean isAvailable();

}

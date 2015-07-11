/*package mpp.library.model.dao.impl;

import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.dao.BookDAO;

*//**
 * @author Anil
 *
 *//*
public class BookDAOImpl extends AbstractSerializationDAO<Book>implements BookDAO {

	@Override
	public void save(Book book) {
		writeObject(SerializationFile.BOOK.getValue(), book);
	}

	@Override
	public Book get(String isbn) {
		List<Book> books = getBookList();

		for (Book book : books) {
			if (book.getISBN().equals(isbn)) {
				return book;
			}
		}
		return null;
	}

	@Override
	public void update(Book book) {
		List<Book> books = getBookList();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getISBN().equals(book.getISBN())) {
				books.set(i, book);
			}
		}
		writeObjectList(SerializationFile.BOOK.getValue(), books);
	}

	@Override
	public List<Book> getBookList() {
		return getObjectList(SerializationFile.BOOK.getValue());

	}

	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Book save() {
		// TODO Auto-generated method stub
		return null;
	}

}
*/
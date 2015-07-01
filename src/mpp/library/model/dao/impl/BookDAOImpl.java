package mpp.library.model.dao.impl;

import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.dao.BookDAO;

/**
 * @author Anil
 *
 */
public class BookDAOImpl extends AbstractSerializationDAO<Book>implements BookDAO {

	@Override
	public void save(Book member) {
		writeObject(SerializationFile.BOOK.getValue(), member);
	}

	@Override
	public void addCopy(Book book, int copyNumber) {
		
		Copy copy = new Copy(book, copyNumber);
		copy.setAvailable(true);
		book.getCopies().add(copy);

		List<Book> books = getBookList();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getISBN().equals(book.getISBN())) {
				books.set(i, book);
			}
		}
		
		writeObjectList(SerializationFile.BOOK.getValue(), books);

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
	public Book save() {
		// TODO Auto-generated method stub
		return null;
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

}

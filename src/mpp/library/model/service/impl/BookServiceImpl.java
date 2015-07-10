package mpp.library.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.dao.BookDAO;
import mpp.library.model.dao.impl.BookDAOImpl;
import mpp.library.model.service.BookService;
import mpp.library.model.service.CopyService;

public class BookServiceImpl implements BookService {

	private BookDAO bookDao;
	private CopyService copyService;

	public BookServiceImpl() {
		bookDao = new BookDAOImpl();
		copyService = new CopyServiceImpl();
	}

	@Override
	public void saveBook(Book book, int copiesNum) {

		List<Copy> copyList = new ArrayList<>();
		for (int i = 0; i < copiesNum; i++) {
			Copy copy = new Copy(book, i, true);
			copyService.saveCopy(copy);
			copyList.add(copy);
		}

		book.setCopies(copyList);
		bookDao.save(book);
	}

	@Override
	public Book getBook(String isbn) {
		return bookDao.get(isbn);
	}

	@Override
	public void addCopy(Book book, int copyNumber) {

		for (int i = 0; i < copyNumber; i++) {
			Copy copy = new Copy(book, i, true);
			copyService.saveCopy(copy);
			book.getCopies().add(copy);
		}

		this.updateBook(book);

	}

	@Override
	public void updateBook(Book book) {
		this.bookDao.update(book);
	}

	@Override
	public void updateBookCopy(Book book, Copy copy) {
		List<Book> books = bookDao.getBookList();

		for (Book bk : books) {

			if (bk.getISBN().equals(book.getISBN())) {

				List<Copy> copies = book.getCopies();
				for (int i = 0; i < copies.size(); i++) {

					if (copies.get(i).getCopyNumber() == copy.getCopyNumber()) {

						copies.set(i, copy);
						bk.setCopies(copies);
						updateBook(bk);
						break;

					}
				}

				break;
			}
		}

	}
}

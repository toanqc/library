package mpp.library.model.service.impl;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.impl.PublicationDAODBImpl;
import mpp.library.model.service.BookService;
import mpp.library.model.service.CopyService;
import mpp.library.util.LambdaLibrary;
import mpp.library.util.PublicationType;

public class BookServiceImpl implements BookService {

	private PublicationDAO publicationDAO;
	private CopyService copyService;

	public BookServiceImpl() {
		publicationDAO = new PublicationDAODBImpl();
		copyService = new CopyServiceImpl();
	}

	@Override
	public void saveBook(Book book, int copiesNum) {

		book.setCopies(LambdaLibrary.GENERATE_LIST_OF_COPIES.apply(copiesNum, book));
		publicationDAO.save(book);
	}

	@Override
	public Book getBook(String isbn) {
		return (Book) publicationDAO.get(PublicationType.BOOK, null, isbn);
	}

	@Override
	public boolean addCopy(String isbn, int copyNumber) {

		Book book = getBook(isbn);

		if (book == null) {
			return false;
		}

		for (int i = 0; i < copyNumber; i++) {
			Copy copy = new Copy(book, i, true);
			copyService.saveCopy(copy);
		}
		return true;
	}
}

package mpp.library.model.service.impl;

import mpp.library.model.Book;
import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.dao.CopyDAO;
import mpp.library.model.dao.impl.CopyDAOImpl;
import mpp.library.model.service.BookService;
import mpp.library.model.service.CopyService;
import mpp.library.model.service.PeriodicalService;

public class CopyServiceImpl implements CopyService {

	private CopyDAO copyDao;
	private BookService bookService;
	private PeriodicalService periodicalService;

	public CopyServiceImpl() {
		copyDao = new CopyDAOImpl();
	}

	@Override
	public void saveCopy(Copy copy) {

		copyDao.save(copy);

	}

	@Override
	public void updateCopy(Copy copy) {
		bookService = new BookServiceImpl();
		periodicalService = new PeriodicalServiceImpl();

		if (copy.getPublication() instanceof Book) {

			bookService.updateBookCopy((Book) copy.getPublication(), copy);

		} else if (copy.getPublication() instanceof Periodical) {

			periodicalService.updatePeriodicalCopy((Periodical) copy.getPublication(), copy);

		} else {

			throw new IllegalArgumentException("Not a valid copy");

		}

		copyDao.update(copy);

	}

}

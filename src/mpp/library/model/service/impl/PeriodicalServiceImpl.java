package mpp.library.model.service.impl;

import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.impl.PublicationDAODBImpl;
import mpp.library.model.service.CopyService;
import mpp.library.model.service.PeriodicalService;
import mpp.library.util.LambdaLibrary;
import mpp.library.util.PublicationType;

public class PeriodicalServiceImpl implements PeriodicalService {

	private PublicationDAO publicationDAO;
	private CopyService copyService;

	public PeriodicalServiceImpl() {
		publicationDAO = new PublicationDAODBImpl();
		copyService = new CopyServiceImpl();
	}

	@Override
	public Periodical getPeriodical(String issueNumber, String title) {
		return (Periodical) publicationDAO.get(PublicationType.PERIODICAL, title, issueNumber);
	}

	@Override
	public void savePeriodical(Periodical periodical, int copiesNum) {

		periodical.setCopies(LambdaLibrary.GENERATE_LIST_OF_COPIES.apply(copiesNum, periodical));
		publicationDAO.save(periodical);
	}

	@Override
	public boolean addCopy(String issueNumber, String title, int copyNumber) {
		Periodical periodical = getPeriodical(issueNumber, title);

		if (periodical == null) {
			return false;
		}

		for (int i = 0; i < copyNumber; i++) {
			Copy copy = new Copy(periodical, 1, true);
			copy.setAvailable(true);
			copyService.saveCopy(copy);
		}

		return true;
	}
}

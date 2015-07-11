package mpp.library.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.dao.db.PublicationDAO;
import mpp.library.model.dao.db.impl.PublicationDAODBImpl;
import mpp.library.model.service.CopyService;
import mpp.library.model.service.PeriodicalService;
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

		List<Copy> copies = new ArrayList<>();
		for (int i = 0; i < copiesNum; i++) {
			Copy copy = new Copy(periodical, 1, true);
			copies.add(copy);
		}

		periodical.setCopies(copies);
		publicationDAO.save(periodical);
	}

	@Override
	public void addCopy(Periodical periodical, int copyNumber) {

		for (int i = 0; i < copyNumber; i++) {
			Copy copy = new Copy(periodical, 1, true);
			copy.setAvailable(true);
			copyService.saveCopy(copy);
		}

	}
}

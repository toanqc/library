package mpp.library.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.Copy;
import mpp.library.model.Periodical;
import mpp.library.model.dao.PeriodicalDAO;
import mpp.library.model.dao.impl.PeriodicalDAOImpl;
import mpp.library.model.service.CopyService;
import mpp.library.model.service.PeriodicalService;

public class PeriodicalServiceImpl implements PeriodicalService {

	private PeriodicalDAO periodicalDao;
	private CopyService copyService;

	public PeriodicalServiceImpl() {
		periodicalDao = new PeriodicalDAOImpl();
		copyService = new CopyServiceImpl();
	}

	@Override
	public Periodical getPeriodical(String issueNumber, String title) {
		return periodicalDao.get(issueNumber, title);
	}

	@Override
	public void savePeriodical(Periodical periodical, int copiesNum) {

		List<Copy> copies = new ArrayList<>();
		for (int i = 0; i < copiesNum; i++) {
			Copy copy = new Copy(periodical, 1, true);
			copy.setAvailable(true);
			copyService.saveCopy(copy);
			copies.add(copy);
		}

		periodical.setCopies(copies);
		periodicalDao.save(periodical);

	}

	@Override
	public void addCopy(Periodical periodical, int copyNumber) {
		
		for (int i = 0; i < copyNumber; i++) {
			Copy copy = new Copy(periodical, 1, true);
			copy.setAvailable(true);
			copyService.saveCopy(copy);
			periodical.getCopies().add(copy);
		}

		this.updatePeriodical(periodical);
	}

	@Override
	public void updatePeriodical(Periodical periodical) {
		periodicalDao.update(periodical);
	}

	@Override
	public void updatePeriodicalCopy(Periodical periodical, Copy copy) {
		List<Periodical> periodicals = periodicalDao.getPeriodicalList();

		for (Periodical pc : periodicals) {

			if (periodical.getIssueNumber().equals(pc.getIssueNumber())
					&& periodical.getTitle().equals(pc.getTitle())) {

				List<Copy> copies = periodical.getCopies();
				for (int i = 0; i < copies.size(); i++) {

					if (copies.get(i).getCopyNumber() == copy.getCopyNumber()) {
						copies.set(i, copy);
						updatePeriodical(pc);
						break;
					}
				}
				break;
			}
		}

	}

}

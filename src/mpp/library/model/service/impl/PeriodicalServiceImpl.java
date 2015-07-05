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
	public void savePeriodical(Periodical periodical) {
		Copy copy = new Copy(periodical, 1);
		copy.setAvailable(true);
		copy = copyService.saveCopy(copy);

		List<Integer> copies = new ArrayList<>();
		copies.add(copy.getId());
		periodical.setCopies(copies);
		periodicalDao.save(periodical);

	}

	@Override
	public void addCopy(Periodical periodical, int copyNumber) {
		Copy copy = new Copy(periodical, copyNumber);
		copy.setAvailable(true);
		copy = copyService.saveCopy(copy);

		periodical.getCopies().add(copy.getId());
		this.updatePeriodical(periodical);

	}

	@Override
	public void updatePeriodical(Periodical periodical) {
		periodicalDao.update(periodical);
	}

}

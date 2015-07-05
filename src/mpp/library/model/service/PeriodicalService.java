package mpp.library.model.service;

import mpp.library.model.Periodical;

public interface PeriodicalService {

	Periodical getPeriodical(String issueNumber, String title);

	void savePeriodical(Periodical periodical);

	void addCopy(Periodical periodical, int copyNumber);

	void updatePeriodical(Periodical periodical);
}

package mpp.library.model.service;

import mpp.library.model.Copy;
import mpp.library.model.Periodical;

/**
 * @author Anil
 *
 */
public interface PeriodicalService {

	/**
	 * Gets {@link Periodical} with issueNumber and title.
	 * 
	 * @param issueNumber
	 * @param title
	 * @return {@link Periodical}
	 */
	Periodical getPeriodical(String issueNumber, String title);

	/**
	 * Saves Periodical to DB.
	 * 
	 * @param periodical
	 * @param copiesNum
	 */
	void savePeriodical(Periodical periodical, int copiesNum);

	/**
	 * Adds {@link Periodical} Copy to DB.
	 * 
	 * @param periodical
	 * @param copyNumber
	 */
	void addCopy(Periodical periodical, int copyNumber);

	/**
	 * Simply updates {@link Periodical}. Does nothing to Models related to
	 * {@link Periodical}.
	 * 
	 * @param periodical
	 */
	void updatePeriodical(Periodical periodical);

	/**
	 * Updates the {@link Copy} of the provided {@link Periodical} with provided
	 * {@link Copy}.
	 * 
	 * @param periodical
	 * @param copy
	 */
	void updatePeriodicalCopy(Periodical periodical, Copy copy);
}

package mpp.library.model.service;

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

}

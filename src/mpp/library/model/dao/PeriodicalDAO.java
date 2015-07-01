package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Periodical;

/**
 * @author Anil
 *
 */
public interface PeriodicalDAO {

	void addCopy(Periodical periodical, int copyNumber);

	Periodical get(String issueNumber, String title);

	void save(Periodical periodical);

	List<Periodical> getPeriodicalList();

	boolean isAvailable();
}

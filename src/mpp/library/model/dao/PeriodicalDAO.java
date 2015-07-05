package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Periodical;

/**
 * @author Anil
 *
 */
public interface PeriodicalDAO {

	Periodical get(String issueNumber, String title);

	void save(Periodical periodical);

	List<Periodical> getPeriodicalList();

	boolean isAvailable();

	void update(Periodical periodical);
}

package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Periodical;

/**
 * @author Anil
 *
 */
public interface PeriodicalDAO extends BaseDAO<Periodical> {

	Periodical get(String issueNumber, String title);

	List<Periodical> getPeriodicalList();

	boolean isAvailable();

	void update(Periodical periodical);
}

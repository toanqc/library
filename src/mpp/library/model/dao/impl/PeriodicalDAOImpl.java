/*package mpp.library.model.dao.impl;

import java.util.List;

import mpp.library.model.Periodical;
import mpp.library.model.dao.PeriodicalDAO;

public class PeriodicalDAOImpl extends AbstractSerializationDAO<Periodical>implements PeriodicalDAO {

	@Override
	public void save(Periodical periodical) {
		writeObject(SerializationFile.PERIODICAL.getValue(), periodical);
	}

	@Override
	public List<Periodical> getPeriodicalList() {
		return getObjectList(SerializationFile.PERIODICAL.getValue());
	}

	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Periodical get(String issueNumber, String title) {
		List<Periodical> periodicals = getObjectList(SerializationFile.PERIODICAL.getValue());
		for (Periodical periodical : periodicals) {
			if (periodical.getIssueNumber().equals(issueNumber) && periodical.getTitle().equals(title)) {
				return periodical;
			}
		}
		return null;
	}
	
	@Override
	public void update(Periodical periodical) {
		List<Periodical> periodicals = getPeriodicalList();
		for (int i = 0; i < periodicals.size(); i++) {
			if (periodicals.get(i).getIssueNumber().equals(periodical.getIssueNumber())
					&& periodicals.get(i).getTitle().equals(periodical.getTitle())) {
				periodicals.set(i, periodical);
			}
		}
		writeObjectList(SerializationFile.PERIODICAL.getValue(), periodicals);
	}

	@Override
	public Periodical get(String id) {
		throw new UnsupportedOperationException("Method get of Periodical not support at this moment");
	}

}
*/
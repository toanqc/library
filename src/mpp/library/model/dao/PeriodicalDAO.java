package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Periodical;

public interface PeriodicalDAO {

	Periodical addCopy();

	Periodical get();

	Periodical save();

	List<Periodical> getPeriodicalList();

	boolean isAvailable();
}

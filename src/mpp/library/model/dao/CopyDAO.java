package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Copy;

public interface CopyDAO extends BaseDAO<Copy> {

	void update(Copy copy);

	List<Copy> getCopyList();
}

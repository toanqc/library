package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Copy;

public interface CopyDAO {

	void save(Copy copy);

	int getCount();

	int getNextId();

	void update(Copy copy);

	List<Copy> getCopyList();
	
	Copy getCopy(int id);
}

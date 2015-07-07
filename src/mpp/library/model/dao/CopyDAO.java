package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Copy;

public interface CopyDAO {

	void save(Copy copy);

	void update(Copy copy);

	List<Copy> getCopyList();
	
}

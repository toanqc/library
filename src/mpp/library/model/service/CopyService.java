package mpp.library.model.service;

import mpp.library.model.Copy;

public interface CopyService {

	Copy saveCopy(Copy copy);


	void updateCopy(Copy copy);


	Copy getCopy(int id);

}

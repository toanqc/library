package mpp.library.model.service;

import mpp.library.model.Copy;
import mpp.library.model.Publication;

public interface CopyService {

	/**
	 * Saves {@link Copy} in DB.
	 * 
	 * @param copy
	 */
	void saveCopy(Copy copy);

	/**
	 * Updates {@link Copy} in Copy File and also updates associated Publication
	 * file.
	 * 
	 * @param copy
	 */
	void updateCopy(Copy copy);

	Copy getAvailableCopy(Publication pub);
}

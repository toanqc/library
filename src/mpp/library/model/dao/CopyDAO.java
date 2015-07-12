package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Copy;
import mpp.library.model.Publication;

public interface CopyDAO extends BaseDAO<Copy> {

	Copy update(Copy copy);

	/**
	 * Get Copies related to provided publication id.
	 * 
	 * @param pubId
	 * @return {@link List} of Copies
	 */
	List<Copy> getByPubId(int pubId);
	
	Copy getAvailableCopy(Publication pub);

}

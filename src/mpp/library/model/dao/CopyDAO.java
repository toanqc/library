package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.Copy;

public interface CopyDAO extends BaseDAO<Copy> {

	Copy update(Copy copy);

	/**
	 * Get Copies related to provided publication id.
	 * 
	 * @param pubId
	 * @return {@link List} of Copies
	 */
	List<Copy> getByPubId(int pubId);

}

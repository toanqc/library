package mpp.library.model.dao;

import mpp.library.model.Address;

public interface AddressDAO extends BaseDAO<Address> {

	/**
	 * Update the address with new information, the true if update successfully
	 * 
	 * @param address
	 *            Address
	 * @return boolean
	 */
	boolean update(Address address);
}

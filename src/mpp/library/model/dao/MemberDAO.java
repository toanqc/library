package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.LibraryMember;

public interface MemberDAO extends BaseDAO<LibraryMember> {

	/**
	 * Return the list of members
	 * 
	 * @return List
	 */
	List<LibraryMember> getList();

	/**
	 * Update the library member with new information, the true if update
	 * successfully
	 * 
	 * @param member
	 *            LibraryMember
	 * @return boolean
	 */
	boolean update(LibraryMember member);

	/**
	 * Auto generate the member id when adding new member
	 * 
	 * @return int
	 */
	int generateId();
}

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
	 * Get the library member information by member id
	 * 
	 * @param memberId
	 *            the member id to be found
	 * @return the found library member information
	 */
	LibraryMember getByMemberId(String memberId);

	/**
	 * Generate the new member id
	 * @return the new member id
	 */
	String geneateMemberId();
}

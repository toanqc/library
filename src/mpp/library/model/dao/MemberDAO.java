package mpp.library.model.dao;

import java.util.List;

import mpp.library.model.LibraryMember;

public interface MemberDAO extends BaseDAO<LibraryMember> {

	/**
	 * 
	 * @return
	 */
	List<LibraryMember> getList();

	/**
	 * 
	 * @param member
	 * @return
	 */
	boolean update(LibraryMember member);

	int generateId();
}

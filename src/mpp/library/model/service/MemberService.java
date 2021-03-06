package mpp.library.model.service;

import java.util.List;

import mpp.library.model.LibraryMember;

/**
 * @author Toan Quach
 */
public interface MemberService {

	/**
	 * Saves {@link LibraryMember} into serialization file
	 * 
	 * @param member
	 */
	void saveMember(LibraryMember member);

	/**
	 * Update {@link LibraryMember} based on member id to serialization file
	 * 
	 * @param member
	 * @return
	 */
	boolean updateMember(LibraryMember member);

	/**
	 * Return the list of {@link LibraryMember}
	 * 
	 * @return List
	 */
	List<LibraryMember> getList();

	/**
	 * Return the {@link LibraryMember} with matching id
	 * 
	 * @param id
	 *            the id in table
	 * @return {@link LibraryMember}
	 */
	LibraryMember get(int id);

	/**
	 * Return the {@link LibraryMember} with matching member idid
	 * 
	 * @param id
	 *            the library member id
	 * @return {@link LibraryMember}
	 */
	LibraryMember getByMemberId(String memberId);
	
	/**
	 * Generate new member id for new library member
	 * 
	 * @return the unique library member id
	 */
	String generateMemberId();
}

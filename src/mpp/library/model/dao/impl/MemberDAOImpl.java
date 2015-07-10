package mpp.library.model.dao.impl;

/**
 */
import java.util.List;

import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;

/**
 * Member DAO provide save / update / get / getList method
 * 
 * @author Toan Quach
 *
 */
public class MemberDAOImpl extends AbstractSerializationDAO<LibraryMember>implements MemberDAO {

	@Override
	public LibraryMember save(LibraryMember member) {
		this.writeObject(SerializationFile.MEMBER.getValue(), member);
		return member;
	}

	@Override
	public LibraryMember get(int id) {
		List<LibraryMember> memberList = getList();
		for (LibraryMember libraryMember : memberList) {
			if (id == libraryMember.getId()) {
				return libraryMember;
			}
		}

		return null;
	}

	@Override
	public List<LibraryMember> getList() {
		return this.getObjectList(SerializationFile.MEMBER.getValue());
	}

	@Override
	public boolean update(LibraryMember member) {
		List<LibraryMember> memberList = getList();
		for (int i = 0; i < memberList.size(); i++) {
			LibraryMember lm = memberList.get(i);
			if (member.getMemberId() == lm.getMemberId()) {
				memberList.set(i, member);
				this.writeObjectList(SerializationFile.MEMBER.getValue(), memberList);
				return true;
			}
		}
		return false;
	}

	@Override
	public LibraryMember getByMemberId(String memberId) {
		List<LibraryMember> memberList = getList();
		for (LibraryMember libraryMember : memberList) {
			if (memberId.equals(libraryMember.getMemberId())) {
				return libraryMember;
			}
		}

		return null;
	}

	@Override
	public String geneateMemberId() {
		List<LibraryMember> memberList = getList();
		if (memberList == null || memberList.isEmpty()) {
			return "1";
		}

		return String.valueOf(memberList.size() + 1);
	}
}

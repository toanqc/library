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
public class MemberDAOImpl extends AbstractSerializationDAO<LibraryMember>
		implements MemberDAO {

	@Override
	public void save(LibraryMember member) {
		this.writeObject(SerializationFile.MEMBER.getValue(), member);
	}

	@Override
	public LibraryMember get(String id) {
		List<LibraryMember> memberList = getList();
		for (LibraryMember libraryMember : memberList) {
			if (id.equals(String.valueOf(libraryMember.getMemberId()))) {
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
				this.writeObjectList(SerializationFile.MEMBER.getValue(),
						memberList);
				return true;
			}
		}
		return false;
	}

	@Override
	public int generateId() {
		List<LibraryMember> memberList = this.getList();
		if (memberList != null && !memberList.isEmpty()) {
			return memberList.size() + 1;
		}
		return 1;
	}
}

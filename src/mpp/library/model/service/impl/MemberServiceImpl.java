package mpp.library.model.service.impl;

import java.util.List;

import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.impl.MemberDAOImpl;
import mpp.library.model.service.MemberService;

public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDAO;

	public MemberServiceImpl() {
		memberDAO = new MemberDAOImpl();
	}

	@Override
	public void saveMember(LibraryMember member) {
		memberDAO.save(member);
	}

	@Override
	public boolean updateMember(LibraryMember member) {
		return memberDAO.update(member);

	}

	@Override
	public List<LibraryMember> getList() {
		return memberDAO.getList();
	}

	@Override
	public LibraryMember get(String id) {
		return memberDAO.get(id);
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

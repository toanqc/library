package mpp.library.model.service.impl;

import java.util.List;

import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;
import mpp.library.model.dao.db.connection.DataAccessFactory;
import mpp.library.model.service.MemberService;

public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDAO;

	public MemberServiceImpl() {
		memberDAO = (MemberDAO) DataAccessFactory.getDAOImpl(MemberDAO.class);
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
	public LibraryMember get(int id) {
		return memberDAO.get(id);
	}

	@Override
	public String generateMemberId() {
		return memberDAO.geneateMemberId();
	}

	@Override
	public LibraryMember getByMemberId(String memberId) {
		return memberDAO.getByMemberId(memberId);
	}
}

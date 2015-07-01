package mpp.library.model.dao.impl;

import java.util.List;

import mpp.library.model.Address;
import mpp.library.model.LibraryMember;
import mpp.library.model.dao.MemberDAO;

public class TestDAO {

	public static void main(String[] args) {
		TestDAO testDAO = new TestDAO();
		// testDAO.testSaveMember();
		testDAO.testGetList();
		// testDAO.testGet();
		// testDAO.testUpdate();
	}

	public void testSaveMember() {
		Address address = new Address("407 S D St", "Fairfield", "IA", 52556);
		LibraryMember member = new LibraryMember(1, "Toan", "Quach",
				"515-346-4204", address);

		MemberDAO memberDAO = new MemberDAOImpl();
		memberDAO.save(member);
	}

	public void testGetList() {
		MemberDAO memberDAO = new MemberDAOImpl();
		List<LibraryMember> list = memberDAO.getList();

		for (LibraryMember libraryMember : list) {
			System.out.println(libraryMember.getMemberId());
		}
	}

	public void testGet() {
		MemberDAO memberDAO = new MemberDAOImpl();
		LibraryMember member = memberDAO.get("1");
		System.out.println(member.getMemberId() + " " + member.getFirstName());
	}

	public void testUpdate() {
		Address address = new Address("1000 N 4th St", "Fairfield", "IA", 52557);
		LibraryMember member = new LibraryMember(1, "Bao", "Pham", "300-000-0000",
				address);
		member.setMemberId(1);

		MemberDAO memberDAO = new MemberDAOImpl();
		memberDAO.update(member);

		List<LibraryMember> list = memberDAO.getList();
		for (LibraryMember libraryMember : list) {
			System.out.println(libraryMember.getMemberId() + " "
					+ libraryMember.getFirstName());
		}
	}
}

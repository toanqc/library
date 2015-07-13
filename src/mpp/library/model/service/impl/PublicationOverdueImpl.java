package mpp.library.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import mpp.library.model.CheckoutRecordEntry;
import mpp.library.model.LibraryMember;
import mpp.library.model.PublicationOverdueRecord;
import mpp.library.model.service.CheckoutService;
import mpp.library.model.service.MemberService;
import mpp.library.model.service.OverdueCalculator;
import mpp.library.util.LambdaLibrary;

/**
 * @author Anil
 *
 */
public class PublicationOverdueImpl implements OverdueCalculator<PublicationOverdueRecord> {

	private MemberService memberService;
	private CheckoutService checkoutService;

	public PublicationOverdueImpl() {
		memberService = new MemberServiceImpl();
		checkoutService = new CheckoutServiceImpl();
	}

	@Override
	public List<PublicationOverdueRecord> getOverdueRecords() {

		List<LibraryMember> libraryMembers = memberService.getList();
		List<PublicationOverdueRecord> publicationOverdueRecords = new ArrayList<>();

		for (LibraryMember libraryMember : libraryMembers) {
			List<CheckoutRecordEntry> checkoutRecordEntries = checkoutService
					.getOverdueCheckoutRecordEntryByMemberId(libraryMember.getId());

			publicationOverdueRecords = LambdaLibrary.PUBLICATION_OVERDUE_RECORD_LAMBDA.apply(checkoutRecordEntries,
					libraryMember);
		}
		return publicationOverdueRecords;

	}

}

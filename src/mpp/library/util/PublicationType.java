package mpp.library.util;

public enum PublicationType {

	BOOK("Book"), PERIODICAL("Periodical");

	String publicationType;

	PublicationType(String publicationType) {
		this.publicationType = publicationType;
	}

	String getValue() {
		return this.publicationType;
	}
}

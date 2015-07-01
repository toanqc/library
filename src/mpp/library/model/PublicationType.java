package mpp.library.model;

public enum PublicationType {
	
	BOOK("Book"), PERIODICAL("Periodical");
	
	String publicationType;
	
	PublicationType(String type) {
		this.publicationType = type;
	}

	public String getValue() {
		return this.publicationType;
	}
}

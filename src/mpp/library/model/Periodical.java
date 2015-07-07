package mpp.library.model;

public class Periodical extends Publication {

	private static final long serialVersionUID = -441236339168212465L;

	private String issueNumber;

	public Periodical(String title, String issueNumber) {
		this.title = title;
		this.issueNumber = issueNumber;
		
	}
	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

}

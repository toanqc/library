package mpp.library.model;

import java.io.Serializable;
import java.util.List;

public class Publication implements Serializable {

	private static final long serialVersionUID = 3211163956723292734L;

	protected String title;
	protected int maxCheckoutLength;
	protected List<Integer> copies;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMaxCheckoutLength() {
		return maxCheckoutLength;
	}

	public void setMaxCheckoutLength(int maxCheckoutLength) {
		this.maxCheckoutLength = maxCheckoutLength;
	}

	public List<Integer> getCopies() {
		return copies;
	}

	public void setCopies(List<Integer> copies) {
		this.copies = copies;
	}

}

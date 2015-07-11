package mpp.library.model;

import java.io.Serializable;
import java.util.List;

public class Publication implements Serializable {

	private static final long serialVersionUID = 3211163956723292734L;

	protected String title;
	protected int maxCheckoutLength;
	protected List<Copy> copies;
	protected int id;

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

	public List<Copy> getCopies() {
		return copies;
	}

	public void setCopies(List<Copy> copies) {
		this.copies = copies;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Publication [title=" + title + ", maxCheckoutLength=" + maxCheckoutLength + ", copies=" + copies
				+ ", id=" + id + "]";
	}

}

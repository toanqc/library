package mpp.library.model;

import java.io.Serializable;

public class Copy implements Serializable {

	private static final long serialVersionUID = 3241319027263293899L;

	private int copyNumber;
	private boolean isAvailable;
	private Publication publication;
	private int id;

	public Copy(int id, Publication pub, int copyNumber, boolean isAvailable) {
		this.id = id;
		this.publication = pub;
		this.copyNumber = copyNumber;
		this.isAvailable = isAvailable;
	}

	public Copy(Publication pub, int copyNumber, boolean isAvailable) {
		this.publication = pub;
		this.copyNumber = copyNumber;
		this.isAvailable = isAvailable;
	}

	
	public int getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean getAvailable() {
		return this.isAvailable;
	}

	public Publication getPublication() {
		return this.publication;
	}

	public void setPublication(Publication pub) {
		this.publication = pub;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Copy [copyNumber=" + copyNumber + ", isAvailable=" + isAvailable + ", id=" + id + "]";
	}

}

package mpp.library.model;

import java.io.Serializable;

public class Copy implements Serializable {

	private static final long serialVersionUID = 3241319027263293899L;

	private int copyNumber;
	private Checkoutable checkoutable;
	private boolean isAvailable;
	private Publication publication;

	public Copy(Publication pub, int copyNumber) {
		this.publication = pub;
		this.copyNumber = copyNumber;
	}

	public int getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(int copyNumber) {
		this.copyNumber = copyNumber;
	}

	public Checkoutable getCheckoutable() {
		return checkoutable;
	}

	public void setCheckoutable(Checkoutable checkoutable) {
		this.checkoutable = checkoutable;
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

}

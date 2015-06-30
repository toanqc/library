package mpp.library.model;

import java.util.List;

public class Book extends Publication implements Checkoutable {

	private static final long serialVersionUID = -6787991247823942688L;

	private String ISBN;
	private List<Author> authorList;

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}

	@Override
	public CheckoutRecordEntry checkout() {
		// TODO Auto-generated method stub
		return null;
	}

}

package mpp.library.model;

import java.util.List;

public class Book extends Publication{

	private static final long serialVersionUID = -6787991247823942688L;

	private String ISBN;
	private List<Author> authorList;

	public Book(String ISBN) {
		this.ISBN = ISBN;
	}
	
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
	
	public Book(int id, String isbn, String title, int maxCheckoutLength) {
		this.id = id;
		this.ISBN = isbn;
		this.title = title;
		this.maxCheckoutLength = maxCheckoutLength;
	}

}

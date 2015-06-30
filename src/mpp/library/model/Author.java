package mpp.library.model;

import java.util.List;

public class Author extends Person {

	private static final long serialVersionUID = 3485880844682032355L;

	private String credentials;
	private String bio;
	private List<Book> books;

	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}

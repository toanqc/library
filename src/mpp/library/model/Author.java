package mpp.library.model;

import java.util.List;

public class Author extends Person {

	private static final long serialVersionUID = 3485880844682032355L;

	private String bio;
	private List<Book> books;
	private int id;
	private int addressId;

	public Author() {
		// empty constructor
	}
	
	public Author(String firstName, String lastName, String phone, String bio, Address address) {
		super(firstName, lastName, phone, address);
		this.bio = bio;
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

	@Override
	public String toString() {
		return "Author [bio=" + bio + ", books=" + books + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setAddressId(int addrId) {
		this.addressId = addrId;
	}

	public int getAddressId() {
		return this.addressId;
	}
}

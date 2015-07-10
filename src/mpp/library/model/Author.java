package mpp.library.model;

import java.util.List;

public class Author extends Person {

	private static final long serialVersionUID = 3485880844682032355L;

	private String credentials;
	private String bio;
	private List<Book> books;
	private int id;
	private int addressId;
	private String firstName;
	private String lastName;
	private String phone;

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

	@Override
	public String toString() {
		return "Author [credentials=" + credentials + ", bio=" + bio + ", books=" + books + "]";
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setAddressId(int addrId) {
		this.addressId = addrId;
	}
	
	public int getAddressId() {
		return this.addressId;
	}
	
	public void setFirstName(String f) {
		this.firstName = f;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setLastName(String l) {
		this.lastName = l;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setPhone(String f) {
		this.phone = f;
	}
	
	public String getPhone() {
		return this.phone;
	}

}

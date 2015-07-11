package mpp.library.model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 451126961827667000L;

	private String firstName;
	private String lastName;
	private String phone;
	private Address address;
	

	public Person() {
		// default constructor, this must have because of Author class
	}

	public Person(String firstName, String lastName, String phone,
			Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}

	public String getFirstName() {
		return firstName.trim();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName.trim();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone.trim();
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}

package mpp.library.model;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = -3719734384138316102L;

	private int id;
	private String street;
	private String city;
	private String state;
	private int zip;

	public Address(String street, String city, String state, int zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getStreet() {
		return street.trim();
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city.trim();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state.trim();
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public void setId(int Id) {
		this.id = Id;
	}

	public int getId() {
		return this.id;
	}
}

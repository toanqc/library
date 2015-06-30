package mpp.library.model;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = -3719734384138316102L;

	private String street;
	private String city;
	private String state;
	private int zip;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
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

}

package mpp.library.model;

import java.io.Serializable;

import javafx.beans.NamedArg;

public class Address implements Serializable {

	private static final long serialVersionUID = -3719734384138316102L;

	private int id;
	private String street;
	private String city;
	private String state;
	private int zip;

	public Address(@NamedArg("street") String street, String city, String state, int zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

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
	
	public void setID(int Id) {
		this.id = Id;
	}
	
	public int getID() {
		return this.id;
	}

}

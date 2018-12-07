package com.cg.capbook.beans;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Id;
@Embeddable
public class UpdateUser {
private String city;
private String state;
private String country;

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
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public UpdateUser() {
	super();
}
public UpdateUser(String city, String state, String country) {
	super();
	this.city = city;
	this.state = state;
	this.country = country;

}

}

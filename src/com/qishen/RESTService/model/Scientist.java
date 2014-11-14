package com.qishen.RESTService.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Scientist {
	private String uid;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String dateOfDeath = null;
	
	public Scientist() {
		uid = UUID.randomUUID().toString();
	}
	
	
	public void setUID(String id) {
		this.uid = id;
	}
	
	public String getUID() {
		return uid;
	}
	
	public void setFirstName(String fn) {
		this.firstName = fn;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String ln) {
		this.lastName = ln;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setDateOfBirth(String dob) {
		this.dateOfBirth = dob;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfDeath(String dod) {
		this.dateOfDeath = dod;
	}
	
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	

}

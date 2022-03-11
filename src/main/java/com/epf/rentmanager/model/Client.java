package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Client {

	private int id;
	private String lastname;
	private String firstname;
	private String email;
	private LocalDate birthdate;

	public Client(int id, String lastname, String firstname, String email, LocalDate birthdate) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.birthdate = birthdate;
	}
	
	public Client() {
		
	}

	public int getId() {
		return id;
	}


	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname.toUpperCase();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", email=" + email
				+ ", birthdate=" + birthdate + "]";
	}

}

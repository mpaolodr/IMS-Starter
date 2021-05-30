package com.qa.ims.persistence.domain;

public class Customer {

	private Long id;
	private String firstname;
	private String surname;
	private String email;
	private String address;

	public Customer(String firstname, String surname, String address) {
		this.firstname = firstname;
		this.surname = surname;
		this.address = address;
	}

	public Customer(String firstname, String surname, String address, String email) {
		this(firstname, surname, address);
		this.email = email;
	}
	
	public Customer(Long id, String firstname, String surname, String address) {
		this(firstname, surname, address);
		this.id = id;
	}
	
	public Customer(Long id, String firstname, String surname, String address, String email) {
		this(id, firstname, surname, address);
		this.email = email;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		
		if (this.email != null) {
			return this.email;
		}
		
		else {
			return "";
		}
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		
		String customerEmail = this.email != null ? this.email : "N/A";
		
		return "Customer ID: " + this.id + ", Firstname: " + this.firstname + ", Surname: " + this.surname + ", Email: " + customerEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (getFirstName() == null) {
			if (other.getFirstName() != null)
				return false;
		} else if (!getFirstName().equals(other.getFirstName()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

}

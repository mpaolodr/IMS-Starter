package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CustomerTest {
	
	
	@Test
	public void testGetID() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		
		assertEquals(Long.valueOf(1L), customer.getId());
	}
	
	@Test
	public void testSetID() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		customer.setId(1L);
		
		assertEquals(Long.valueOf(1L), customer.getId());
	}
	
	@Test
	public void testGetFirstName() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		
		assertEquals("TEST FNAME", customer.getFirstName());
	}
	
	@Test
	public void testSetFirstName() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		customer.setFirstName("TEST FNAME CHANGE");
		
		assertEquals("TEST FNAME CHANGE", customer.getFirstName());
	}
	
	@Test
	public void testGetSurname() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		
		assertEquals("TEST SNAME", customer.getSurname());
	}
	
	@Test
	public void testSetSurname() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		customer.setSurname("TEST SNAME CHANGE");
		
		assertEquals("TEST SNAME CHANGE", customer.getSurname());
	}
	
	@Test
	public void testGetEmailWhenNull() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		
		assertEquals("", customer.getEmail());
	}
	
	@Test
	public void testGetEmail() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR", "TEST@EMAIL.com");
		
		assertEquals("TEST@EMAIL.com", customer.getEmail());
	}
	
	@Test
	public void testSetEmailWhenNull() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		customer.setEmail("TESTEMAIL@null.com");
		
		assertNotEquals("", customer.getEmail());
	}
	
	@Test
	public void testSetEmail() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR", "TEST@EMAIL.com");
		customer.setEmail("NEWEMAIL@test.com");
		
		assertNotEquals("TEST@EMAIL.com", customer.getEmail());
	}
	
	@Test 
	public void testGetAddress() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		
		assertEquals("TEST ADDR", customer.getAddress());
	}
	
	@Test
	public void testSetAddress() {
		Customer customer = new Customer("TEST FNAME", "TEST SNAME", "TEST ADDR");
		customer.setAddress("TEST ADDR CHANGE");
		
		assertEquals("TEST ADDR CHANGE", customer.getAddress());
	}
	
	@Test
	public void testToStringWhenEmailIsNull() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		String res = "Customer ID: 1, Firstname: TEST FNAME, Surname: TEST SNAME, Email: N/A, Address: TEST ADDR";
		
		assertEquals(res, customer.toString());
	}
	
	@Test
	public void testToStringWhenEmailIsNotNull() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR", "TEST@email.com");
		String res = "Customer ID: 1, Firstname: TEST FNAME, Surname: TEST SNAME, Email: TEST@email.com, Address: TEST ADDR";
		
		assertEquals(res, customer.toString());
	}
	

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Customer.class).verify();
	}
	
	

}

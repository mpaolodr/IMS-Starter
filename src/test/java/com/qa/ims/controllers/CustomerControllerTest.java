package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.CustomerController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	@Mock
	private Utils utils;

	@Mock
	private CustomerDAO dao;

	@InjectMocks
	private CustomerController controller;

	@Test
	public void testCreateWithoutEmail() {
		final String F_NAME = "barry", L_NAME = "scott", ADDR = "USA";
		final Customer created = new Customer(F_NAME, L_NAME, ADDR);

		Mockito.when(utils.getString()).thenReturn(F_NAME, L_NAME, ADDR, "N");
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(4)).getString();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}
	
	@Test
	public void testCreateWithEmail() {
		final String F_NAME = "barry", L_NAME = "scott", ADDR = "USA", EMAIL = "TEST@EMAIL.com";
		final Customer created = new Customer(F_NAME, L_NAME, ADDR, EMAIL);

		Mockito.when(utils.getString()).thenReturn(F_NAME, L_NAME, ADDR, "Y", EMAIL);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(utils, Mockito.times(5)).getString();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "jordan", "harrison", "UK", "jordan@harrison.com"));

		Mockito.when(dao.readAll()).thenReturn(customers);

		assertEquals(customers, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testReadAllFromEmptyDB() {
		List<Customer> customers = new ArrayList<>();

		Mockito.when(dao.readAll()).thenReturn(customers);

		assertEquals(customers, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testUpdateEmptyDB() {
		
		List<Customer> customers = new ArrayList<Customer>();
		
		Mockito.when(this.dao.readAll()).thenReturn(customers);

		assertEquals(null, this.controller.update());
	}

	@Test
	public void testUpdateFirstName() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "chris", "harrison", "USA", "N/A");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(1L,1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer, customer);
		Mockito.when(this.utils.getString()).thenReturn("F", "chris");
		Mockito.when(this.dao.update(updated, "firstname")).thenReturn(updated);

		assertEquals(updated, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(2)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "firstname");
	}
	
	@Test
	public void testUpdateSurname() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "jordan", "michael", "USA", "N/A");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(1L,1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer, customer);
		Mockito.when(this.utils.getString()).thenReturn("s", "michael");
		Mockito.when(this.dao.update(updated, "surname")).thenReturn(updated);

		assertEquals(updated, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(2)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "surname");
	}
	
	@Test
	public void testUpdateAddress() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "jordan", "harrison", "UK", "N/A");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(1L,1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer, customer);
		Mockito.when(this.utils.getString()).thenReturn("a", "UK");
		Mockito.when(this.dao.update(updated, "address")).thenReturn(updated);

		assertEquals(updated, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(2)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "address");
	}
	
	@Test
	public void testUpdateEmail() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "jordan", "harrison", "USA", "test@email.com");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(1L,1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer, customer);
		Mockito.when(this.utils.getString()).thenReturn("e", "test@email.com");
		Mockito.when(this.dao.update(updated, "email")).thenReturn(updated);

		assertEquals(updated, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(2)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "email");
	}
	
	@Test
	public void testUpdateAll() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "michael", "jordan", "Chicago, IL, USA", "jumpman@email.com");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(1L,1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer);
		Mockito.when(this.utils.getString()).thenReturn("all", "michael", "jordan", "jumpman@email.com", "Chicago, IL, USA");
		Mockito.when(this.dao.update(updated, "all")).thenReturn(updated);

		assertEquals(updated, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(5)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "all");
	}		
	
	@Test
	public void testUpdateInvalidID() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "michael", "jordan", "Chicago, IL, USA", "jumpman@email.com");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(2L);
		Mockito.when(this.dao.read(2L)).thenReturn(null);
		
		assertEquals(null, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(1)).read(2L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.dao, Mockito.times(0)).update(updated, "all");
	}		
	
	@Test
	public void testUpdateInvalidCommand() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		Customer updated = new Customer(1L, "michael", "jordan", "Chicago, IL, USA", "jumpman@email.com");

		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(this.utils.getLong()).thenReturn(1L,1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer);
		Mockito.when(this.utils.getString()).thenReturn("invalid");
		
		assertEquals(null, controller.update());
		
		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.dao, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.dao, Mockito.times(0)).update(updated, "all");
	}		

	@Test
	public void testDelete() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		
		
		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer);
		Mockito.when(utils.getString()).thenReturn("Y");
		Mockito.when(dao.delete(1L)).thenReturn(1);

		assertEquals(1L, this.controller.delete());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
		Mockito.verify(dao, Mockito.times(1)).read(1L);
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(1)).delete(1L);
	}
	
	@Test
	public void testAbortDelete() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		
		
		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer);
		Mockito.when(utils.getString()).thenReturn("N");
	
		assertEquals(0, this.controller.delete());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
		Mockito.verify(dao, Mockito.times(1)).read(1L);
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(0)).delete(1L);
	}
	
	@Test
	public void testInvalidDelete() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		
		
		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(customer);
		Mockito.when(utils.getString()).thenReturn("X");
	
		assertEquals(0, this.controller.delete());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
		Mockito.verify(dao, Mockito.times(1)).read(1L);
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(dao, Mockito.times(0)).delete(1L);
	}
	
	@Test
	public void testInvalidIDDelete() {
		
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer(1L, "jordan", "harrison", "USA", "N/A");
		customers.add(customer);
		
		
		Mockito.when(this.dao.readAll()).thenReturn(customers);
		Mockito.when(utils.getLong()).thenReturn(2L);
		Mockito.when(this.dao.read(2L)).thenReturn(null);

	
		assertEquals(0, this.controller.delete());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
		Mockito.verify(dao, Mockito.times(1)).read(2L);
		Mockito.verify(utils, Mockito.times(1)).getLong();
		
	}
	@Test
	public void testDeleteEmptyDB() {
		
		List<Customer> customers = new ArrayList<Customer>();
		
		Mockito.when(this.dao.readAll()).thenReturn(customers);

		assertEquals(0, this.controller.delete());
	}

}

package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class OrderTest {
	
	@Test
	public void testGetId() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			items.add(new Item(i + 1, "TEST ITEM", price + 1));
		}
		
		Order order = new Order(1L, customer, items);
		
		assertEquals(Long.valueOf(1), order.getId());
	}
	
	@Test
	public void testGetCustomer() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			items.add(new Item(i + 1, "TEST ITEM", price + 1));
		}
		
		Order order = new Order(1L, customer, items);
		
		assertEquals(customer, order.getCustomer());
	}
	
	@Test 
	public void testGetItems() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			items.add(new Item(i + 1, "TEST ITEM", price + 1));
		}
		
		Order order = new Order(1L, customer, items);
		
		assertEquals(items, order.getItems());
	}
	
	@Test
	public void testSetItems() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			items.add(new Item(i + 1, "TEST ITEM", price + 1));
		}
		
		Order order = new Order(1L, customer, items);
		
		List<Item> newItems = new ArrayList<Item>();
		newItems.add(new Item(10L, "NEW ITEM", 100.99));
		
		order.setItems(newItems);
		
		assertNotEquals(items, order.getItems());
		
	}
	
	@Test
	public void testGetItemQuantity() {
		
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			
			if (i == 0L) {
				items.add(new Item(i + 1, "TEST ITEM", price + 1));
				items.add(new Item(i + 1, "TEST ITEM", price + 1));				
			}
			
			else {
				items.add(new Item(i + 1, "TEST ITEM", price + 1));								
			}
		}
		
		Order order = new Order(1L, customer, items);
		
		assertEquals(2, order.getItemQuantity(new Item(1L, "TEST ITEM", 11.99)));
	}
	
	@Test
	public void testAddItems() {
		
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
	
			items.add(new Item(i + 1, "TEST ITEM", price + 1));
		}
		
		Order order = new Order(1L, customer, items);
		order.addItems(new Item(1L, "TEST ITEM", 11.99), 2);
		
		assertEquals(3, order.getItemQuantity(new Item(1L, "TEST ITEM", 11.99)));
		
	}
	
	@Test
	public void testRemoveMoreThanQuantity() {
		
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
	
			items.add(new Item(i + 1, "TEST ITEM", price + 1));
		}
		
		Order order = new Order(1L, customer, items);	
		order.removeItems(new Item(1L, "TEST ITEM", 11.99), 2);
		
		assertEquals(0, order.getItemQuantity(new Item(1L, "TEST ITEM", 11.99)));
		
	}
	
	@Test public void testRemoveLessThanQuantity() {
		
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
	
			if (i == 0L) {
				items.add(new Item(i + 1, "TEST ITEM", price + 1));
				items.add(new Item(i + 1, "TEST ITEM", price + 1));				
				items.add(new Item(i + 1, "TEST ITEM", price + 1));				
				items.add(new Item(i + 1, "TEST ITEM", price + 1));				
			}
			
			else {
				items.add(new Item(i + 1, "TEST ITEM", price + 1));								
			}
		}
		
		Order order = new Order(1L, customer, items);	
		order.removeItems(new Item(1L, "TEST ITEM", 11.99), 2);
		
		assertEquals(2, order.getItemQuantity(new Item(1L, "TEST ITEM", 11.99)));
		
	}
	
	
	@Test
	public void testGetQuantity() {
		
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			items.add(new Item(i + 1, "TEST ITEM", price + 1));								
		}
		
		Order order = new Order(1L, customer, items);	
		
		assertEquals(items.size(), order.getQuantity());
	}
	
	@Test
	public void testGetTotalPrice() {
		
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		
		for (Long i = 0L; i < 5; i++) {
			Double price = 10.99;
			items.add(new Item(i + 1, "TEST ITEM", price + 1));								
		}
		
		Order order = new Order(1L, customer, items);	
		
		double total = 0.0;
		
		for (Item i: items) {
			total += i.getPrice();
		}
		
		assertTrue(total == order.getTotalPrice());
	}
	
	@Test
	public void testToString() {
		Customer customer = new Customer(1L, "TEST FNAME", "TEST SNAME", "TEST ADDR");
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "TEST ITEM", 10.99);
		items.add(item);	
		items.add(item);	
		
		Order order = new Order(1L, customer, items);
		
		String customerDetails = "Customer Name: " + customer.getFirstName() + " " + customer.getSurname() + "\n=======================";
		String orderDetails = "\nOrder ID: " + order.getId() + "\nOrder Details: \n" + item.toString() + ", Quantity: " + order.getItemQuantity(item) + "\n";
		String totalDetails = "=======================\nTotal Price: " + item.getPrice() * 2 + ", Total Number of Items: 2";
		
		assertEquals(customerDetails + orderDetails + totalDetails, order.toString());
		
	}
	

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Order.class).verify();
	}

}

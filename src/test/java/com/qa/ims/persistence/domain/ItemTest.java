package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ItemTest {
	
	@Test
	public void testGetIdWhenIDIsNull() {
		Item item = new Item("TEST ITEM", 10.99);
		
		assertNull(item.getId());
	}
	
	@Test
	public void testGetIdWhenIDIsNotNull() {
		Item item = new Item(1L, "TEST ITEM", 10.99);
		
		assertEquals(Long.valueOf(1), item.getId());
	}
	
	@Test
	public void testGetName() {
		Item item = new Item("TEST ITEM", 10.99);
		
		assertEquals("TEST ITEM", item.getName());
	}
	
	@Test
	public void testSetName() {
		Item item = new Item("TEST ITEM", 10.99);
		item.setName("TEST ITEM NAME CHANGE");
		
		assertNotEquals("TEST ITEM", item.getName());
	}
	
	@Test
	public void testGetPrice() {
		Item item = new Item("TEST ITEM", 10.99);
		
		assertEquals(Double.valueOf(10.99), item.getPrice());
	}
	
	@Test
	public void testSetPrie() {
		Item item = new Item("TEST ITEM", 10.99);
		item.setPrice(11.99);
		
		assertNotEquals(Double.valueOf(10.99), item.getPrice());
	}
	
	@Test
	public void testToString() {
		Item item = new Item(1L, "TEST ITEM", 10.99);
		String res = "Item ID: 1, Item Name: TEST ITEM, Item Price: 10.99";
		
		assertEquals(res, item.toString());
	}
	
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Item.class).verify();
	}

}

package com.qa.ims.persistence.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	
	private static final OrderDAO DAO = new OrderDAO();
	private static final CustomerDAO customerDAO = new CustomerDAO();
	private static final ItemDAO itemDAO = new ItemDAO();


	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@BeforeClass
	public static void deleteAll() {
		for (Order order : DAO.readAll()) {
			DAO.delete(order.getId());
		}
	}
	
	@Test
	public void testReadAllEmptyDB() {
		assertEquals(new ArrayList<>(), DAO.readAll());
	}
	
	@Test
	public void testReadAll() {
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		List<Order> orders = DAO.readAll();
		
		
		boolean orderReturnedFromDB = false;
		
		for (Order o: orders) {
			if (createdOrder.getCustomer().equals(o.getCustomer())) {
				orderReturnedFromDB = true;
				break;
			}
		}
		
		assertTrue(orderReturnedFromDB);
		
		DAO.delete(createdOrder.getId());
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
		
	}
	
	@Test
	public void testReadLatestFromEmptyDB() {
		assertNull(DAO.readLatest());
	}
	
	@Test
	public void testReadLatest() {
		
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		
		assertEquals(createdOrder.getId(), DAO.readLatest().getId());
		
		DAO.delete(createdOrder.getId());
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
		
	}
	
	@Test
	public void testReadFromEmptyDB() {
		assertNull(DAO.read(1L));
	}
	
	@Test
	public void testRead() {
		
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		
		assertEquals(customer, DAO.read(createdOrder.getId()).getCustomer());
		
		DAO.delete(createdOrder.getId());
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
		
	}
	
	@Test
	public void testCreateError() {
//		customer doesn't exist in DB
		Customer customer = new Customer("John", "Wick", "ANYWHERE");
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		
		assertNull(createdOrder);
		
		itemDAO.delete(item.getId());
		
	}
	
	@Test 
	public void testCreate() {
		
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		
		assertNotNull(createdOrder);
		
		DAO.delete(createdOrder.getId());
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
		
	}
	
	@Test
	public void testUpdateError() {
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		assertNull(DAO.update(new Order(customer, items), "items"));
		
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
	
	}
	
	@Test
	public void testUpdate() {
		
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		
		List<Item> newItems = new ArrayList<Item>();
		newItems.add(item);
		newItems.add(item);

		Order updatedOrder = DAO.update(new Order(createdOrder.getId(), customer, newItems), "items");
		
		assertNotEquals(createdOrder.getQuantity(), updatedOrder.getQuantity());
		
		DAO.delete(createdOrder.getId());
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
		
	}
	
	@Test
	public void testDeleteEmptyDB() {
		assertEquals(0, DAO.delete(2));
	}
	
	@Test
	public void testDelete() {
		
		Customer customer = customerDAO.create(new Customer("John", "Wick", "ANYWHERE"));
		List<Item> items = new ArrayList<Item>();
		
		Item item = itemDAO.create(new Item("9MM", 100.00));
		items.add(item);
		
		Order createdOrder = DAO.create(new Order(customer, items));
		
		assertEquals(1, DAO.delete(createdOrder.getId()));
		
		DAO.delete(createdOrder.getId());
		customerDAO.delete(customer.getId());
		itemDAO.delete(item.getId());
		
		
	}
	
	

}

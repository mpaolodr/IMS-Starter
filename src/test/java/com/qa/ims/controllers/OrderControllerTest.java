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

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private Utils utils;

	@Mock
	private OrderDAO orderDAO;

	@Mock
	private ItemDAO itemDAO;

	@Mock
	private CustomerDAO customerDAO;

	@InjectMocks
	private OrderController controller;

	@Test
	public void testReadAll() {
		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		Order order = new Order(1L, customer, items);
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);

		assertEquals(orders, this.controller.readAll());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();

	}

	@Test
	public void testReadAllFromEmptyDB() {
		List<Order> orders = new ArrayList<Order>();

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);

		assertEquals(orders, this.controller.readAll());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();

	}

	@Test
	public void testCreate() {
		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);

		Order order = new Order(customer, items);
		Order created = new Order(1L, customer, items);

		Mockito.when(this.customerDAO.readAll()).thenReturn(customers);
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L, 1L, 1L);
		Mockito.when(this.customerDAO.read(1L)).thenReturn(customer);
		Mockito.when(this.itemDAO.read(1L)).thenReturn(item);
		Mockito.when(this.utils.getString()).thenReturn("n");
		Mockito.when(this.orderDAO.create(order)).thenReturn(created);

		assertEquals(created, this.controller.create());

		Mockito.verify(this.customerDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(3)).getLong();
		Mockito.verify(this.customerDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.orderDAO, Mockito.times(1)).create(order);

	}

	@Test
	public void testCreateNoCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();

		Item item = new Item(1L, "Ball", 10.99);
		items.add(item);

		Mockito.when(this.customerDAO.readAll()).thenReturn(customers);
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);

		assertEquals(null, this.controller.create());

		Mockito.verify(this.customerDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();

	}

	@Test
	public void testCreateNoItems() {
		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		customers.add(customer);

		Mockito.when(this.customerDAO.readAll()).thenReturn(customers);
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);

		assertEquals(null, this.controller.create());

		Mockito.verify(this.customerDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();

	}

	@Test
	public void testCreateNoItemsAndCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();

		Mockito.when(this.customerDAO.readAll()).thenReturn(customers);
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);

		assertEquals(null, this.controller.create());

		Mockito.verify(this.customerDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();

	}

	@Test
	public void testCreateInvalidCustomerID() {
		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);

		Order order = new Order(customer, items);
		Order created = new Order(1L, customer, items);

		Mockito.when(this.customerDAO.readAll()).thenReturn(customers);
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(2L);
		Mockito.when(this.customerDAO.read(2L)).thenReturn(null);

		assertEquals(null, this.controller.create());

		Mockito.verify(this.customerDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.customerDAO, Mockito.times(1)).read(2L);

	}

	@Test
	public void testCreateInvalidItemID() {
		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);

		Order order = new Order(customer, items);
		Order created = new Order(1L, customer, items);

		Mockito.when(this.customerDAO.readAll()).thenReturn(customers);
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L, 2L, 1L, 1L);
		Mockito.when(this.customerDAO.read(1L)).thenReturn(customer);
		Mockito.when(this.itemDAO.read(2L)).thenReturn(null);
		Mockito.when(this.itemDAO.read(1L)).thenReturn(item);
		Mockito.when(this.utils.getString()).thenReturn("n");
		Mockito.when(this.orderDAO.create(order)).thenReturn(created);

		assertEquals(created, this.controller.create());

		Mockito.verify(this.customerDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(4)).getLong();
		Mockito.verify(this.customerDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(2L);
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.orderDAO, Mockito.times(1)).create(order);

	}

	@Test
	public void testUpdateAddItems() {

		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();
		List<Item> newItems = new ArrayList<Item>();
		List<Order> orders = new ArrayList<Order>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);

		newItems.add(item);
		newItems.add(item);

		Order order = new Order(1L, customer, items);
		orders.add(order);

		Order updated = new Order(1L, customer, newItems);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);
		Mockito.when(this.utils.getLong()).thenReturn(1L, 1L, 1L);
		Mockito.when(this.orderDAO.read(1L)).thenReturn(order);
		Mockito.when(this.utils.getString()).thenReturn("a", "n");
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);
		Mockito.when(this.itemDAO.read(1L)).thenReturn(item);
		Mockito.when(this.orderDAO.update(order, "items")).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(3)).getLong();
		Mockito.verify(this.orderDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.orderDAO, Mockito.times(1)).update(order, "items");
	}

	@Test
	public void testUpdateRemoveItems() {

		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();
		List<Item> newItems = new ArrayList<Item>();
		List<Order> orders = new ArrayList<Order>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);
		items.add(item);
		items.add(item);

		newItems.add(item);
		newItems.add(item);

		Order order = new Order(1L, customer, items);
		orders.add(order);

		Order updated = new Order(1L, customer, newItems);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);
		Mockito.when(this.utils.getLong()).thenReturn(1L, 1L, 1L);
		Mockito.when(this.orderDAO.read(1L)).thenReturn(order);
		Mockito.when(this.utils.getString()).thenReturn("r", "n");
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);
		Mockito.when(this.itemDAO.read(1L)).thenReturn(item);
		Mockito.when(this.orderDAO.update(order, "items")).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(3)).getLong();
		Mockito.verify(this.orderDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.orderDAO, Mockito.times(1)).update(order, "items");

	}

	@Test
	public void testUpdateInvalidCommand() {

		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();
		List<Item> newItems = new ArrayList<Item>();
		List<Order> orders = new ArrayList<Order>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);
		items.add(item);
		items.add(item);

		newItems.add(item);
		newItems.add(item);

		Order order = new Order(1L, customer, items);
		orders.add(order);

		Order updated = new Order(1L, customer, newItems);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);
		Mockito.when(this.utils.getLong()).thenReturn(1L, 1L, 1L);
		Mockito.when(this.orderDAO.read(1L)).thenReturn(order);
		Mockito.when(this.utils.getString()).thenReturn("x", "r", "n");
		Mockito.when(this.itemDAO.readAll()).thenReturn(items);
		Mockito.when(this.itemDAO.read(1L)).thenReturn(item);
		Mockito.when(this.orderDAO.update(order, "items")).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(3)).getLong();
		Mockito.verify(this.orderDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(3)).getString();
		Mockito.verify(this.itemDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.itemDAO, Mockito.times(1)).read(1L);
		Mockito.verify(this.orderDAO, Mockito.times(1)).update(order, "items");

	}

	@Test
	public void testUpdateEmptyOrders() {

		List<Order> orders = new ArrayList<Order>();

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);

		assertEquals(null, this.controller.update());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();

	}

	@Test
	public void testUpdateInvalidOrderID() {

		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();
		List<Order> orders = new ArrayList<Order>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);
		items.add(item);
		items.add(item);

		Order order = new Order(1L, customer, items);
		orders.add(order);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);
		Mockito.when(this.utils.getLong()).thenReturn(2L);
		Mockito.when(this.orderDAO.read(2L)).thenReturn(null);

		assertEquals(null, this.controller.update());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.orderDAO, Mockito.times(1)).read(2L);

	}

	@Test
	public void testDelete() {

		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();
		List<Order> orders = new ArrayList<Order>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);
		items.add(item);
		items.add(item);

		Order order = new Order(1L, customer, items);
		orders.add(order);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.utils.getString()).thenReturn("y");
		Mockito.when(this.orderDAO.delete(1L)).thenReturn(1);

		assertEquals(1, this.controller.delete());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.orderDAO, Mockito.times(1)).delete(1L);

	}

	@Test
	public void testAbortDelete() {

		List<Customer> customers = new ArrayList<Customer>();
		List<Item> items = new ArrayList<Item>();
		List<Order> orders = new ArrayList<Order>();

		Customer customer = new Customer(1L, "Michael", "Jordan", "Chicago, IL, USA");
		Item item = new Item(1L, "Ball", 10.99);

		customers.add(customer);
		items.add(item);
		items.add(item);
		items.add(item);

		Order order = new Order(1L, customer, items);
		orders.add(order);

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.utils.getString()).thenReturn("n");

		assertEquals(0, this.controller.delete());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(1)).getString();

	}

	@Test
	public void testDeleteFromEmptyOrders() {

		List<Order> orders = new ArrayList<Order>();

		Mockito.when(this.orderDAO.readAll()).thenReturn(orders);

		assertEquals(0, this.controller.delete());

		Mockito.verify(this.orderDAO, Mockito.times(1)).readAll();

	}

}

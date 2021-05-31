package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private ItemDAO itemDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, CustomerDAO customerDAO, ItemDAO itemDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.customerDAO = customerDAO;
		this.itemDAO = itemDAO;
		this.utils = utils;
	}

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		
		if (orders.size() == 0) {
			LOGGER.info("No orders exist in the database");
		}
		
		else {
			
			for (Order order : orders) {
				LOGGER.info(order);
			}
			
		}


		return orders;
	}

	@Override
	public Order create() {
		
		List<Customer> customers = customerDAO.readAll();
		List<Item> items = itemDAO.readAll();
		
		if (customers.size() == 0 & items.size() == 0) {
			LOGGER.info("No customers and items exist in the database. \nOrders need to be associated with a customer and an item.");
			return null;
		}
		
		else if (customers.size() == 0) {
			LOGGER.info("No customers exist in the database. \nOrders need to be associated with a customer.");
			return null;
		}
		
		else if(items.size() == 0) {
			LOGGER.info("No items exist in the database. \nOrders need to have items.");
			return null;
		}
		
		LOGGER.info("List of Customers:");
		for (int i = 0; i < customers.size(); i++) {
			LOGGER.info(i + 1 + ". " + customers.get(i));
		}
		
		LOGGER.info("\nPlease enter the customer ID this order is associated with: ");
		Long customerID = utils.getLong();
		
		Customer customer = customerDAO.read(customerID);
		
		if (customer == null) {
			LOGGER.info("Customer with ID " + customerID + " doesn't exist");
			return null;
		}
		
		LOGGER.info("\nList of available items: ");
		for (int i = 0; i < items.size(); i++) {
			LOGGER.info(i + 1 + ". " + items.get(i));
		}

		boolean addItems = true;
		List<Item> itemsOrdered = new ArrayList<Item>();

		do {
			
//			LOGGER.info("Here's a list of items available: ");
//			List<Item> availableItems = this.itemDAO.readAll();
//			
//			for (Item item: availableItems) {
//				LOGGER.info(item);
//			}

			LOGGER.info("\nPlease choose an item ID you wish to order: ");
			Long itemID = utils.getLong();
			Item item = itemDAO.read(itemID);
			
			if (item == null) {
				LOGGER.info("The item with ID " + itemID + " doesn't exist");
				continue;
			}


			LOGGER.info("How many of this would you want to add?");
			Long quantity = utils.getLong();

			for (int i = 0; i < quantity; i++) {
				itemsOrdered.add(item);
			}

			LOGGER.info("Item successfully added to your order!");
			LOGGER.info("Do you wish to add items to your current order? Y / N");
			String option = utils.getString();

			if (option.toLowerCase().equals("n")) {
				addItems = false;
			}

		} while (addItems);

		LOGGER.info("Your order has been created. Thank you!");

		return orderDAO.create(new Order(customer, itemsOrdered));
	}

	@Override
	public Order update() {
		List<Order> ordersAvailable = orderDAO.readAll();
		
		if (ordersAvailable.size() == 0) {
			LOGGER.info("No orders exist in the database");
			return null;
		}
		
		LOGGER.info("Below is a list of available orders to choose from: \n=======================================\n" );
		for(Order order: ordersAvailable) {
			LOGGER.info("Order ID: " + order.getId() + ", Customer: " + order.getCustomer().getFirstName() + " " + order.getCustomer().getSurname());
		}
		
		LOGGER.info("\n=======================================\n");
		
		LOGGER.info("Please enter the id of the order you wish to edit: ");
		Long orderID = utils.getLong();
		
	
		Order currentOrder = orderDAO.read(orderID);
		
		

		boolean continueOperation = true;

		do {

			LOGGER.info("What do you want to do with this order?");
			LOGGER.info("=======================================");
			LOGGER.info("\sA - ADD Items\n\sR - REMOVE Items");
			String option = utils.getString();
			LOGGER.info("=======================================");

			if (!option.toLowerCase().equals("a") & !option.toLowerCase().equals("r")) {
				LOGGER.info("Sorry, that command is invalid. Please choose a different option.");
				continue;
			}

			List<Item> currentItems = this.itemDAO.readAll();

			LOGGER.info("Here are our current list of items: ");
			for (Item item : currentItems) {
				LOGGER.info(item);
			}

			if (option.toLowerCase().equals("a")) {

				LOGGER.info("Please enter the ID of the item you want to add: ");
				Long itemID = utils.getLong();

				LOGGER.info("How many of these items do you want to add? ");
				Long quantity = utils.getLong();

				currentOrder.addItems(itemDAO.read(itemID), quantity);

			}

			else {

				LOGGER.info("Please enter the ID of the item you want to remove: ");
				Long itemID = utils.getLong();

				LOGGER.info("How many of these items do you want to remove? ");
				Long quantity = utils.getLong();

				currentOrder.removeItems(itemDAO.read(itemID), quantity);

			}

			LOGGER.info("Do you want to continue editing this order? Y / N");
			String continueOption = utils.getString();

			if (continueOption.toLowerCase().equals("n")) {
				continueOperation = false;
			}

		} while (continueOperation);

		return orderDAO.update(currentOrder, "items");

	}

	@Override
	public int delete() {
		List<Order> orders = orderDAO.readAll();
		
		if (orders.size() == 0) {
			LOGGER.info("No orders exist in the database");
			return 0;
			
		}
		
		LOGGER.info("Here's a list of orders in the database:\n");
		for (int i = 0; i < orders.size(); i++) {
			LOGGER.info(i + 1 + ". " + orders.get(i) + "\n");
		}

		LOGGER.info("Please enter the ID of the order you wan to delete: ");
		Long orderID = utils.getLong();

		LOGGER.info("\nThis order will be removed from the database: ");
		LOGGER.info(orderDAO.read(orderID));

		LOGGER.info("\nAre you sure you want to procede with this operation? Y / N");
		String option = utils.getString();

		if (option.toLowerCase().equals("y")) {
			LOGGER.info("Order was successfully removed");
			return orderDAO.delete(orderID);
		}

		else {
			LOGGER.info("Aborting operation");
			return 0;
		}

	}

}

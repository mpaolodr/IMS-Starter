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

		for (Order order : orders) {
			LOGGER.info(order);
		}

		return orders;
	}

	@Override
	public Order create() {
		LOGGER.info("Please enter the customer ID this order is associated with: ");
		Long customerID = utils.getLong();
		Customer customer = customerDAO.read(customerID);

		boolean addItems = true;
		List<Item> itemsOrdered = new ArrayList<Item>();

		do {

			LOGGER.info("Please choose an item ID you wish to order: ");
			Long itemID = utils.getLong();
			Item item = itemDAO.read(itemID);

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

		LOGGER.info("Please enter the id of the order you wish to edit: ");
		Long orderID = utils.getLong();
		Order currentOrder = orderDAO.read(orderID);

		boolean continueOperation = true;

		do {

			LOGGER.info("What do you want to do with this order?");
			LOGGER.info("=======================================");
			LOGGER.info("\sA - ADD Items\n\sR - REMOVE Items");
			LOGGER.info("=======================================");
			String option = utils.getString();

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
		LOGGER.info("Here's a list of orders in the database: ");

		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}

		LOGGER.info("Please enter the ID of the order you wan to delete: ");
		Long orderID = utils.getLong();

		LOGGER.info("This order will be removed from the database: ");
		LOGGER.info(orderDAO.read(orderID));

		LOGGER.info("Are you sure you want to procede with this operation? Y / N");
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

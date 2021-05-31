package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;

/**
 * Takes in customer details for CRUD functionality
 *
 */
public class CustomerController implements CrudController<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();

	private CustomerDAO customerDAO;
	private Utils utils;

	public CustomerController(CustomerDAO customerDAO, Utils utils) {
		super();
		this.customerDAO = customerDAO;
		this.utils = utils;
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Customer> readAll() {
		List<Customer> customers = customerDAO.readAll();
		for (Customer customer : customers) {
			LOGGER.info(customer);
		}
		return customers;
	}

	/**
	 * Creates a customer by taking in user input
	 */
	@Override
	public Customer create() {
		LOGGER.info("Please enter a first name: ");
		String firstName = utils.getString();

		LOGGER.info("Please enter a surname: ");
		String surname = utils.getString();

		LOGGER.info("Please enter an address: ");
		String address = utils.getString();

//		For optional email field
		LOGGER.info("Do you wish to provide an email address? Y / N ");
		String val = utils.getString();

		if (val.toLowerCase().equals("y")) {

			LOGGER.info("Please enter a valid email address: ");
			String email = utils.getString();

			LOGGER.info("Customer created!");
			return customerDAO.create(new Customer(firstName, surname, address, email));

		}

		else {
			LOGGER.info("Customer created!");
			return customerDAO.create(new Customer(firstName, surname, address));

		}

	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Customer update() {

		LOGGER.info("Please enter the id of the customer you would like to update");
		Long id = utils.getLong();

		LOGGER.info("What field do you wish to update? ");
		LOGGER.info("==================================\n");
		LOGGER.info("\s F - firstname \n");
		LOGGER.info("\s S - surname \n");
		LOGGER.info("\s E - email \n");
		LOGGER.info("\s A - address");
		LOGGER.info("\s ALL - all fields\n");

		LOGGER.info("==================================\n");
		String option = utils.getString();

		switch (option.toLowerCase()) {
		case "f":
			Customer customerToEditFirstname = customerDAO.read(id);
			
//			* 'FTE' stands for 'FIRSTNAME-TO-EDIT' (can't come up with unique names for each case) 
			String surnameFTE = customerToEditFirstname.getSurname();
			String emailFTE = customerToEditFirstname.getEmail();
			String addressFTE = customerToEditFirstname.getAddress();

			LOGGER.info("Please enter a new firstname: ");
			String newFirstname = utils.getString();
			
			LOGGER.info("Customer Updated");
			return customerDAO.update(new Customer(id, newFirstname, surnameFTE, addressFTE, emailFTE),
					"firstname");

		case "s":
			
			Customer customerToEditSurname = customerDAO.read(id);
			
//			* 'STE' stands for 'SURNAME-TO-EDIT' (can't come up with unique names for each case) 
			String firstnameSTE = customerToEditSurname.getFirstName();
			String emailSTE = customerToEditSurname.getEmail();
			String addressSTE = customerToEditSurname.getAddress();

			LOGGER.info("Please enter a new surname: ");
			String newSurname = utils.getString();
			
			LOGGER.info("Customer Updated");
			return customerDAO.update(new Customer(id, firstnameSTE, newSurname, addressSTE, emailSTE),
					"surname");

		case "e":
	
			Customer customerToEditEmail = customerDAO.read(id);
			
//			* 'ETE' stands for 'EMAIL-TO-EDIT' (can't come up with unique names for each case) 
			String firstnameETE = customerToEditEmail.getFirstName();
			String surnameETE = customerToEditEmail.getSurname();
			String addressETE = customerToEditEmail.getAddress();

			LOGGER.info("Please enter a new email: ");
			String newEmail = utils.getString();
			
			LOGGER.info("Customer Updated");
			return customerDAO.update(new Customer(id, firstnameETE, surnameETE, addressETE, newEmail),
					"email");

			

		case "a":
			Customer customerToEditAddress = customerDAO.read(id);
			
//			* 'ATE' stands for 'ADDRESS-TO-EDIT' (can't come up with unique names for each case) 
			String firstnameATE = customerToEditAddress.getFirstName();
			String surnameATE = customerToEditAddress.getSurname();
			String emailATE = customerToEditAddress.getEmail();

			LOGGER.info("Please enter a new address: ");
			String newAddress = utils.getString();
			
			LOGGER.info("Customer Updated");
			return customerDAO.update(new Customer(id, firstnameATE, surnameATE, newAddress, emailATE),
					"address");


		default:

			LOGGER.info("Please enter a new first name: ");
			String firstName = utils.getString();

			LOGGER.info("Please enter a new surname: ");
			String surname = utils.getString();

			LOGGER.info("Please enter a new email: ");
			String email = utils.getString();

			LOGGER.info("Please enter a new address: ");
			String address = utils.getString();
			
			LOGGER.info("Customer Updated");
			return customerDAO.update(new Customer(id, firstName, surname, address, email), "all");
			
		}

		

	}

	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the customer you would like to delete");
		Long id = utils.getLong();

		LOGGER.info("Are you sure you wish to delete this record? Y / N ");
		String option = utils.getString();

		if (option.toLowerCase().equals("y")) {
			LOGGER.info("Customer records were successfully deleted");
			return customerDAO.delete(id);

		}

		else {
			LOGGER.info("Aborting operation");
			return 0;
		}

	}

}

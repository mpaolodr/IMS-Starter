package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAO implements Dao<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Customer modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("customer_id");
		String firstname = resultSet.getString("firstname");
		String surname = resultSet.getString("surname");
		String address = resultSet.getString("address");
		return new Customer(id, firstname, surname, address);
	}

	/**
	 * Reads all customers from the database
	 * 
	 * @return A list of customers
	 */
	@Override
	public List<Customer> readAll() {
		try (Connection con = DBUtils.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM customer");
				ResultSet resultSet = ps.executeQuery();) {
			List<Customer> customers = new ArrayList<>();
			while (resultSet.next()) {
				customers.add(modelFromResultSet(resultSet));
			}
			return customers;
		}

		catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Customer readLatest() {
		try (Connection con = DBUtils.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM customer ORDER BY customer_id DESC LIMIT 1");
				ResultSet resultSet = ps.executeQuery();) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		}

		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;
	}

	/**
	 * Creates a customer in the database
	 * 
	 * @param customer - takes in a customer object. id will be ignored
	 */
	@Override
	public Customer create(Customer customer) {
		try (Connection con = DBUtils.getInstance().getConnection();) {

			if (customer.getEmail().equals("")) {

				PreparedStatement noEmailPS = con
						.prepareStatement("INSERT INTO customer(firstname, surname, address) VALUES (?, ?, ?)");

				noEmailPS.setString(1, customer.getFirstName());
				noEmailPS.setString(2, customer.getSurname());
				noEmailPS.setString(3, customer.getAddress());
				noEmailPS.executeUpdate();

			}

			else {

				PreparedStatement withEmailPS = con.prepareStatement(
						"INSERT INTO customer(firstname, surname, address, email) VALUES (?, ?, ?, ?)");

				withEmailPS.setString(1, customer.getFirstName());
				withEmailPS.setString(2, customer.getSurname());
				withEmailPS.setString(3, customer.getAddress());
				withEmailPS.setString(4, customer.getEmail());
				withEmailPS.executeUpdate();

			}

			return readLatest();

		}

		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;

	}

	@Override
	public Customer read(Long id) {
		try (Connection con = DBUtils.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");) {
			ps.setLong(1, id);

			try (ResultSet resultSet = ps.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		}

		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;
	}

	/**
	 * Updates a customer in the database
	 * 
	 * @param customer - takes in a customer object, the id field will be used to
	 *                 update that customer in the database
	 * @return
	 */
	@Override
	public Customer update(Customer customer, String fieldToEdit) {
		try (Connection con = DBUtils.getInstance().getConnection();) {

			switch (fieldToEdit.toLowerCase()) {

			case "firstname":

				PreparedStatement firstnamePS = con.prepareStatement("UPDATE customer SET firstname = ? WHERE customer_id = ?");

				firstnamePS.setString(1, customer.getFirstName());
				firstnamePS.setLong(2, customer.getId());

				firstnamePS.executeUpdate();

				break;

			case "surname":

				PreparedStatement surnamePS = con.prepareStatement("UPDATE customer SET surname = ? WHERE customer_id = ?");

				surnamePS.setString(1, customer.getSurname());
				surnamePS.setLong(2, customer.getId());

				surnamePS.executeUpdate();

				break;

			case "email":

				PreparedStatement emailPS = con.prepareStatement("UPDATE customer SET email = ? WHERE customer_id = ?");

				emailPS.setString(1, customer.getEmail());
				emailPS.setLong(2, customer.getId());

				emailPS.executeUpdate();

				break;

			case "address":

				PreparedStatement addressPS = con.prepareStatement("UPDATE customer SET email = ? WHERE customer_id = ?");

				addressPS.setString(1, customer.getAddress());
				addressPS.setLong(2, customer.getId());

				addressPS.executeUpdate();

				break;

			default:

				PreparedStatement ps = con.prepareStatement(
						"UPDATE customer SET firstname = ?, surname = ?, email = ?, address = ? WHERE customer_id = ?");

				ps.setString(1, customer.getFirstName());
				ps.setString(2, customer.getSurname());
				ps.setString(3, customer.getEmail());
				ps.setString(4, customer.getAddress());
				ps.setLong(5, customer.getId());

				ps.executeUpdate();

				break;

			}

			return read(customer.getId());
		}

		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;

	}

	/**
	 * Deletes a customer in the database
	 * 
	 * @param id - id of the customer
	 */
	@Override
	public int delete(long id) {
		try (Connection con = DBUtils.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement("DELETE FROM customer WHERE customer_id = ?");) {

			ps.setLong(1, id);
			return ps.executeUpdate();

		}

		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return 0;

	}

}

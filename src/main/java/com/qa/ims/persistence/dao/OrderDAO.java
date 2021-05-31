package com.qa.ims.persistence.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();
	
	private List<String> convertToList(String input) {
		
		List<String> result = new ArrayList<String>();
		
		String temp = "";
		
		
		for (int i = 0; i < input.length() - 1; i++) {
			
			
				
			if (input.charAt(i) != '[' & input.charAt(i) != '"' & input.charAt(i) != ' ' & input.charAt(i) != ',') {
				
				temp += Character.toString(input.charAt(i));
				
				if (input.charAt(i + 1) == '"' | (input.charAt(i + 1) == ',' | input.charAt(i + 1) == ']')) {
					
					result.add(temp);
					temp = "";
				}
				
			}
			
		}
		
		return result;
	}
	
	
//	T modelFromResultSet(ResultSet resultSet) throws SQLException;
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
	
		Long orderId = resultSet.getLong("order_id");
		String customerFirstname = resultSet.getString("firstname");
		String customerSurname = resultSet.getString("surname");
		String customerAddress = resultSet.getString("address");
		String customerEmail = resultSet.getString("address");
		
		
		List<String> itemNames = convertToList((String)resultSet.getObject("item_names"));
		List<String> itemPrices = convertToList((String)resultSet.getObject("item_prices"));
		List<String> itemIDs = convertToList((String)resultSet.getObject("item_ids"));
		
		List<Item> items = new ArrayList<Item>();
		
		
		
		for (int i = 0; i < itemNames.size(); i++) {
			items.add(new Item(Long.parseLong(itemIDs.get(i)), itemNames.get(i), Double.parseDouble(itemPrices.get(i))));
		}
	
		Customer customer;
		
		if (customerEmail == null) {
			customer = new Customer(customerFirstname, customerSurname, customerAddress);
		}
		
		else {
			customer = new Customer(customerFirstname, customerSurname, customerAddress, customerEmail);
		}
		
		return new Order(orderId, customer, items);

	}


	@Override
	public List<Order> readAll() {
		try {

			Connection con = DBUtils.getInstance().getConnection();
			String sql = "SELECT o.order_id, c.firstname, c.surname, c.email, c.address, JSON_ARRAYAGG(i.item_id) as item_ids, JSON_ARRAYAGG(i.item_name) as item_names, JSON_ARRAYAGG(i.item_price) as item_prices FROM orders_item ot INNER JOIN orders o ON ot.order_id = o.order_id INNER JOIN item i ON ot.item_id = i.item_id INNER JOIN customer c ON o.customer_id = c.customer_id GROUP BY o.order_id";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<Order> orders = new ArrayList<Order>();
			
			while (rs.next()) {
				orders.add(modelFromResultSet(rs));
			}
			
			return orders;

		}

		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return new ArrayList<>();
	}
	
	
	public Order readLatest() {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sqlLine1 = "SELECT o.order_id, c.firstname, c.surname, c.email, c.address, JSON_ARRAYAGG(i.item_id) as item_ids, JSON_ARRAYAGG(i.item_name) as item_names, JSON_ARRAYAGG(i.item_price) as item_prices FROM orders_item ot";
			String sqlLine2 = " INNER JOIN orders o ON ot.order_id = o.order_id";
			String sqlLine3 = " INNER JOIN item i ON ot.item_id = i.item_id";
			String sqlLine4 = " INNER JOIN customer c ON o.customer_id = c.customer_id";
			String sqlLine5 = " GROUP BY o.order_id";
			String sqlLine6 = " ORDER BY o.order_id DESC LIMIT 1";
			PreparedStatement ps = con.prepareStatement(sqlLine1 + sqlLine2 + sqlLine3 + sqlLine4 + sqlLine5 + sqlLine6);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			return modelFromResultSet(rs);
		}
		
		catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}
	

	@Override
	public Order read(Long id) {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sqlLine1 = "SELECT o.order_id, c.firstname, c.surname, c.email, c.address, JSON_ARRAYAGG(i.item_id) as item_ids, JSON_ARRAYAGG(i.item_name) as item_names, JSON_ARRAYAGG(i.item_price) as item_prices FROM orders_item ot";
			String sqlLine2 = " INNER JOIN orders o ON ot.order_id = o.order_id";
			String sqlLine3 = " INNER JOIN item i ON ot.item_id = i.item_id";
			String sqlLine4 = " INNER JOIN customer c ON o.customer_id = c.customer_id";
			String sqlLine5 = " WHERE o.order_id = ?";
			String sqlLine6 = " GROUP BY o.order_id";
			PreparedStatement ps = con.prepareStatement(sqlLine1 + sqlLine2 + sqlLine3 + sqlLine4 + sqlLine5 + sqlLine6);
			
			ps.setLong(1, id);
			
			try(ResultSet rs = ps.executeQuery();) {
				rs.next();
				return modelFromResultSet(rs);
			}

		}
		
		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return null;
		
	}

	@Override
	public Order create(Order order) {
		
		Long customerId = order.getCustomer().getId();
		List<Item> itemsOrdered = order.getItems();
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			
//			INSERTING TO ORDERS TABLE
			String orderSQL = "INSERT INTO orders (customer_id) VALUES (?)";
			PreparedStatement orderPS = con.prepareStatement(orderSQL);
			
			orderPS.setLong(1, customerId);
			orderPS.executeUpdate();
			
//			INSERTING TO ORDERS_ITEM TABLE
//			GRAB LATEST ORDER THAT WAS ADDED
			String latestOrderSQL = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1"; 
			PreparedStatement latestOrderPS = con.prepareStatement(latestOrderSQL);
			ResultSet latestOrderRS = latestOrderPS.executeQuery();
			
			latestOrderRS.next();
			Long orderID = latestOrderRS.getLong("order_id");
			
			
			for (Item item: itemsOrdered) {
				
				String intermediarySQL = "INSERT INTO orders_item (order_id, item_id) VALUES (?, ?)";
				PreparedStatement intermediaryPS = con.prepareStatement(intermediarySQL);
				intermediaryPS.setLong(1, orderID);
				intermediaryPS.setLong(2, item.getId());
				
				intermediaryPS.executeUpdate();
			}
			
			
			return readLatest();
			
		} 
		
		catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		
		return null;
	}

	@Override
	public Order update(Order order, String field) {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			
//			REMOVE ALL EXISTING ITEMS UNDER AN ORDER
			String removeItemsSQL = "DELETE FROM orders_item WHERE order_id = ?"; 
			PreparedStatement removeItemsPS = con.prepareStatement(removeItemsSQL);
			removeItemsPS.setLong(1, order.getId());
			removeItemsPS.execute();
			
			
			String intermediarySQL = "INSERT INTO orders_item (order_id, item_id) VALUES (?, ?)";
			PreparedStatement intermediaryPS = con.prepareStatement(intermediarySQL);
			
			for (Item item: order.getItems()) {
				intermediaryPS.setLong(1, order.getId());
				intermediaryPS.setLong(2,  item.getId());
				
				intermediaryPS.addBatch();
			}
			
			intermediaryPS.executeBatch();
			
			return read(order.getId());
			
		}
		
		catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return null;
	
	}

	@Override
	public int delete(long id) {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sql = "DELETE FROM orders WHERE order_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, id);
			return ps.executeUpdate();
			
		} 
		
		catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return 0;
	}

}

package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAO implements Dao<Item> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
		
		Long id = resultSet.getLong("item_id");
		String itemName = resultSet.getString("item_name");
		Double itemPrice = resultSet.getDouble("item_price");
		
		return new Item(id, itemName, itemPrice);
	
	}
	
	@Override
	public List<Item> readAll() {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sql = "SELECT * FROM item";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			List<Item> items = new ArrayList<>();
			
			while (rs.next()) {
				items.add(modelFromResultSet(rs));
			}
			
			return items;
			
		}
		
		catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return new ArrayList<>();
	}
	
	
	@Override
	public Item read(Long id) {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sql = "SELECT * FROM item WHERE item_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, id);
			
			try {
				ResultSet rs = ps.executeQuery();
				rs.next();
				
				return modelFromResultSet(rs);
				
			} 
			
			catch(Exception e) {
				LOGGER.debug(e);
//				LOGGER.error(e.getMessage());
			}
		
		} 
		
		catch(SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return null;
		
	}
	
	public Item readLatest() {
		
		try {
			Connection con = DBUtils.getInstance().getConnection();
			String sql = "SELECT * FROM item ORDER BY item_id DESC LIMIT 1";
			PreparedStatement ps = con.prepareStatement(sql);
			
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
	public Item create(Item newItem) {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sql = "INSERT INTO item(item_name, item_price) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, newItem.getName());
			ps.setDouble(2, newItem.getPrice());
			
			ps.executeUpdate();
			
			return readLatest();
			
		}
		
		catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		
		return null;
		
	}
	
	@Override
	public Item update(Item item, String field) {
		
		String sql;
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			
			switch(field) {
			
			case "name":
				
				sql = "UPDATE item SET item_name = ? WHERE item_id = ?";
				PreparedStatement namePS = con.prepareStatement(sql);
				
				namePS.setString(1, item.getName());
				namePS.setLong(2, item.getId());
				
				namePS.executeUpdate();
				
				break;
				
			case "price":
				
				sql = "UPDATE item SET item_price = ? WHERE item_id = ?";
				PreparedStatement pricePS = con.prepareStatement(sql);
				
				pricePS.setDouble(1, item.getPrice());
				pricePS.setLong(2, item.getId());
				
				pricePS.executeUpdate();
				
				break;
				
				
			case "all":
				
				sql = "UPDATE item SET item_name = ?, item_price = ? WHERE item_id = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, item.getName());
				ps.setDouble(2, item.getPrice());
				ps.setLong(3, item.getId());
				
				ps.executeUpdate();
				
				break;
				
				
			default:
				
				return null;
			}
			
			return read(item.getId());
		
		}
		
		catch(Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public int delete(long itemId) {
		
		try {
			
			Connection con = DBUtils.getInstance().getConnection();
			String sql = "DELETE FROM item WHERE item_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, itemId);
			return ps.executeUpdate();
			
		}
		
		catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return 0;
	}

	

}

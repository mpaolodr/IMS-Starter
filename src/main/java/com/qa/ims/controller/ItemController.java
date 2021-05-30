package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

public class ItemController implements CrudController<Item> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private ItemDAO itemDAO;
	private Utils utils;
	
	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}
	
	@Override
	public List<Item> readAll() {
		List<Item> items = this.itemDAO.readAll();
		
		for (Item item: items) {
			LOGGER.info(item);
		}
		
		return items;
	}
	
	@Override
	public Item create() {
		
		LOGGER.info("Please enter the name of the item you want to add: ");
		String itemName = this.utils.getString();
		
		LOGGER.info("Please enter the price of this item: ");
		Double itemPrice = this.utils.getDouble();
		
		return itemDAO.create(new Item(itemName, itemPrice));
		
	}
	
	@Override
	public Item update() {
		
		LOGGER.info("Please enter the id of the item you would like to update: ");
		Long id = this.utils.getLong();
		
		LOGGER.info("What field do you wish to update? ");
		LOGGER.info("==================================\n");
		LOGGER.info("\s N - name \n");
		LOGGER.info("\s P - price \n");
		LOGGER.info("\s ALL - all fields\n");
		LOGGER.info("==================================\n");
		String option = this.utils.getString();
		
		switch(option.toLowerCase()) {
		
		case "n":
			
			Item itemToEditName = itemDAO.read(id);
			
			LOGGER.info("Please enter a new name for this item: ");
			String newName = this.utils.getString();
			
			Double priceNTE = itemToEditName.getPrice();
			
			LOGGER.info("Name Updated!");
			return itemDAO.update(new Item(id, newName, priceNTE), "name");
			
		case "p":
			
			Item itemToEditPrice = itemDAO.read(id);
			
			LOGGER.info("Please enter a new price for this item: ");
			Double newPrice = this.utils.getDouble();
			
			String nameNTE = itemToEditPrice.getName();
			
			LOGGER.info("Price Updated!");
			return itemDAO.update(new Item(id, nameNTE, newPrice), "price");
			
			
		default:
			
			LOGGER.info("Please enter a new name for this item: ");
			String name = this.utils.getString();
			
			LOGGER.info("Please enter a new price for this item: ");
			Double price = this.utils.getDouble();
			
			LOGGER.info("Item Updated!");
			return itemDAO.update(new Item(id, name, price), "all");
		}
		
		
	}
	
	@Override
	public int delete() {
		
		LOGGER.info("Please enter the id of the item you would like to delete ");
		Long id = this.utils.getLong();
		
		Item itemToDelete = itemDAO.read(id);
		
		LOGGER.info("Are you sure you want to delete this item? \n: " + itemToDelete.toString() + "\n Y / N");
		String option = this.utils.getString();
		
		if (option.toLowerCase().equals("y")) {
			LOGGER.info("Item was successfully removed from the database");
			return itemDAO.delete(id);
		}
		
		else {
			LOGGER.info("Aborting operation");
			return 0;
		}
		
		
	}
	
}

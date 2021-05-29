package com.qa.ims.persistence.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.utils.Utils;

public enum Domain {

	CUSTOMER("Information about customers"), ITEM("Individual Items"), ORDER("Purchases of items"),
	STOP("To close the application");

	public static final Logger LOGGER = LogManager.getLogger();

	private String description;

	private Domain(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.name() + ": " + this.description;
	}

	public static void printDomains() {
		
		LOGGER.info("============================================\n");
		
		for(int i = 0; i < Domain.values().length; i++) {
			if (i == Domain.values().length - 1) {
				LOGGER.info(Domain.values()[i].getDescription() + "\n");
			} 
			
			else {
				
				LOGGER.info(Domain.values()[i].getDescription());
				
			}
		}
		
		LOGGER.info("============================================\n");
	}

	public static Domain getDomain(Utils utils) {
		Domain domain;
		while (true) {
			try {
				domain = Domain.valueOf(utils.getString().toUpperCase());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		}
		return domain;
	}

}

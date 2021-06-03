package com.qa.ims.persistence.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {

	private Long id;
	private Customer customer;
	private List<Item> items;
	private Integer quantity;
	private Double totalPrice;

	public Order(Customer customer, List<Item> items) {
		this.customer = customer;
		this.items = items;
		this.quantity = items.size();

		Double runningTotal = 0.0;

		for (Item item : items) {
			runningTotal += item.getPrice();
		}

		this.totalPrice = runningTotal;
	}

	public Order(Long id, Customer customer, List<Item> items) {
		this(customer, items);
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getItemQuantity(Item target) {
		int quantity = 0;

		for (Item item : this.items) {
			if (item.getId() == target.getId()) {
				quantity += 1;
			}
		}

		return quantity;
	}

	public void addItems(Item item, long quantityToAdd) {

		for (int i = 0; i < quantityToAdd; i++) {
			this.items.add(item);
		}
	}

	public void removeItems(Item target, long quantityToRemove) {

		int currentNumberOfItem = getItemQuantity(target);

		if (currentNumberOfItem >= quantityToRemove) {

			int currentCount = 0;
			int startIndex = 0;

			while (startIndex < this.items.size()) {
				if (this.items.get(startIndex).getId() == target.getId()) {
					this.items.remove(startIndex);
					currentCount += 1;
				}

				else {

					startIndex += 1;
				}
				
				if (currentCount == quantityToRemove) {
					break;
				}
			}

		}

		else {

			int startIndex = 0;

			while (startIndex < this.items.size()) {

				if (this.items.get(startIndex).getId() == target.getId()) {
					this.items.remove(startIndex);
				}
				
				else {
					startIndex += 1;
					
				}

			}

		}

	}

	public int getQuantity() {
		return this.quantity;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	@Override
	public String toString() {
		String customerDetails = "Customer Name: " + this.customer.getFirstName() + " " + this.customer.getSurname()
				+ "\n=======================";
		String orderDetails = "\nOrder ID: " + this.id + "\nOrder Details: \n";

		Set<String> tracker = new HashSet<String>();

		for (Item item : this.items) {

			if (!tracker.contains(item.getName())) {
				orderDetails += item.toString() + ", Quantity: " + this.getItemQuantity(item) + "\n";
				tracker.add(item.getName());
			}
		}

		String totalDetails = String.format("=======================\nTotal Price: %.2f, Total Number of Items: %d",
				(float) this.getTotalPrice(), this.getQuantity());
		return customerDetails + orderDetails + totalDetails;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;

		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.items == null) ? 0 : this.items.hashCode());
		result = prime * result + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = prime * result + ((this.quantity == null) ? 0 : this.quantity.hashCode());
		result = prime * result + ((this.totalPrice == null) ? 0 : this.totalPrice.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Order other = (Order) obj;

		if (this.getCustomer() == null) {
			if (other.getCustomer() != null) {
				return false;
			}
		}

		else if (!this.getCustomer().equals(other.getCustomer())) {
			return false;
		}

		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		}

		else if (!this.id.equals(other.id)) {
			return false;
		}

		if (this.items == null) {
			if (other.items != null) {
				return false;
			}
		}

		else if (!this.items.equals(other.items)) {
			return false;
		}

		if (this.quantity == null) {

			if (other.quantity != null) {
				return false;
			}
		}

		else if (this.quantity != null && other.quantity != null) {

			if (this.quantity.compareTo(other.quantity) != 0) {

				return false;
			}
		}

		if (this.totalPrice == null) {

			if (other.totalPrice != null) {
				return false;
			}
		}

		else if (this.totalPrice != null && other.totalPrice != null) {

			if (this.totalPrice.compareTo(other.totalPrice) != 0) {

				return false;
			}
		}

		return true;

	}

}

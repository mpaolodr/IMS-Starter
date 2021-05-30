package com.qa.ims.persistence.domain;

import java.util.List;

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
		
		for (Item item: items) {
			runningTotal += item.getPrice();
		}
		
		this.totalPrice = runningTotal;
	}
	
	public Order(Long id, Customer customer, List<Item> items) {
		this(customer, items);
		this.id = id;
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
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public double getTotalPrice() {
		return this.totalPrice;
	}
	
	@Override
	public String toString() {
		String customerDetails = "Customer Name: " + this.customer.getFirstName() + " " + this.customer.getSurname();
		String orderDetails = "Order Details: \n";
		
		for (Item item: this.items) {
			orderDetails += item.toString() + "\n";
		}
		
		return customerDetails + orderDetails;
	}
	
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((this.id == null) ? 0: this.id.hashCode());
		
		for (Item item: this.items) {
			result += prime * result + item.hashCode();
		}
		
		return result + this.customer.hashCode();
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
		
//		compare customer field
		if (this.getCustomer() == null) {
			if (other.getCustomer() != null) {
				return false;
			}
		}
		
		else if (!this.getCustomer().equals(other.getCustomer())) {
			return false;
		}
		
//		compare id field
		if (this.id == null) {
			if (other.id == null) {
				return false;
			}
		}
		
		else if (!this.id.equals(other.id)) {
			return false;
		}
		
		return true;
		
	}
	

}

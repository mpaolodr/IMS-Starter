package com.qa.ims.persistence.domain;

public class Item {
	
	private Long id;
	private String name;
	private Double price;
	
	public Item(String name, Double price) {
		this.name = name;
		this.price = price;
	}
	
	public Item(Long id, String name, Double price) {
		this(name, price);
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public void setPrice(Double newPrice) {
		this.price = newPrice;
	}
	
	@Override
	public String toString() {
		return "Item ID: " + this.id + ", Item Name: " + this.name + ", Item Price: " + this.price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
		
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
		
		Item other = (Item) obj;
		
//		compare name field
		if (getName() == null) {
			
			if (other.getName() != null) {
				return false;
			}
			
		}
		
		else if (!getName().equals(other.getName())) {
			return false;
		}
		
//		compare id field
		if (this.id == null) {
			
			if (other.getId() != null) {
				return false;
			}
		} 
		
		else if (!this.id.equals(other.getId())) {
			return false;
		}
		
		return true;
		
		
	}

}

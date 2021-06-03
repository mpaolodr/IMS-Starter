package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
	@Mock
	private Utils utils;

	@Mock
	private ItemDAO dao;

	@InjectMocks
	private ItemController controller;

	@Test
	public void testReadAll() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item("BioMutant", 10.99);
		items.add(item);

		Mockito.when(this.dao.readAll()).thenReturn(items);

		assertEquals(items, this.controller.readAll());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testReadAllFromEmptyDB() {
		List<Item> items = new ArrayList<Item>();

		Mockito.when(this.dao.readAll()).thenReturn(items);

		assertEquals(items, this.controller.readAll());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testCreate() {
		Item item = new Item("BioMutant", 59.99);

		Mockito.when(this.utils.getString()).thenReturn("BioMutant");
		Mockito.when(this.utils.getDouble()).thenReturn(59.99);
		Mockito.when(this.dao.create(item)).thenReturn(item);

		assertEquals(item, this.controller.create());

		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.utils, Mockito.times(1)).getDouble();
		Mockito.verify(this.dao, Mockito.times(1)).create(item);

	}

	@Test
	public void testUpdateName() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);
		Item updated = new Item(1L, "Borderlands", 59.99);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(item, item);
		Mockito.when(this.utils.getString()).thenReturn("n", "Borderlands");
		Mockito.when(this.dao.update(updated, "name")).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.dao, Mockito.times(2)).read(1L);
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "name");

	}

	@Test
	public void testUpdatePrice() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);
		Item updated = new Item(1L, "BioMutant", 19.99);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(item, item);
		Mockito.when(this.utils.getString()).thenReturn("p");
		Mockito.when(this.utils.getDouble()).thenReturn(19.99);
		Mockito.when(this.dao.update(updated, "price")).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.utils, Mockito.times(1)).getDouble();
		Mockito.verify(this.dao, Mockito.times(2)).read(1L);
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "price");

	}

	@Test
	public void testUpdateAll() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);
		Item updated = new Item(1L, "Borderlands", 19.99);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(item);
		Mockito.when(this.utils.getString()).thenReturn("all", "Borderlands");
		Mockito.when(this.utils.getDouble()).thenReturn(19.99);
		Mockito.when(this.dao.update(updated, "all")).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(2)).getString();
		Mockito.verify(this.utils, Mockito.times(1)).getDouble();
		Mockito.verify(this.dao, Mockito.times(1)).read(1L);
		Mockito.verify(this.dao, Mockito.times(1)).update(updated, "all");

	}

	@Test
	public void testUpdateEmptyDB() {
		List<Item> items = new ArrayList<Item>();

		Mockito.when(this.dao.readAll()).thenReturn(items);

		assertEquals(null, this.controller.update());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();

	}

	@Test
	public void testUpdateInvalidId() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(2L);
		Mockito.when(this.dao.read(2L)).thenReturn(null);

		assertEquals(null, this.controller.update());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.dao, Mockito.times(1)).read(2L);

	}

	@Test
	public void testUpdateInvalidCommand() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);
		Item updated = new Item(1L, "Borderlands", 19.99);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(item);
		Mockito.when(this.utils.getString()).thenReturn("a");

		assertEquals(null, this.controller.update());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).read(1L);

	}

	@Test
	public void testDelete() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(item);
		Mockito.when(this.utils.getString()).thenReturn("y");
		Mockito.when(this.dao.delete(1L)).thenReturn(1);

		assertEquals(1, this.controller.delete());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.dao, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		Mockito.verify(this.dao, Mockito.times(1)).delete(1L);
	}

	@Test
	public void testDeleteFromEmptyDB() {
		List<Item> items = new ArrayList<Item>();

		Mockito.when(this.dao.readAll()).thenReturn(items);

		assertEquals(0, this.controller.delete());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testDeleteInvalidID() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(2L);
		Mockito.when(this.dao.read(2L)).thenReturn(null);

		assertEquals(0, this.controller.delete());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.dao, Mockito.times(1)).read(2L);
	
	}
	
	@Test
	public void testDeleteInvalidCommand() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item(1L, "BioMutant", 59.99);
		items.add(item);

		Mockito.when(this.dao.readAll()).thenReturn(items);
		Mockito.when(this.utils.getLong()).thenReturn(1L);
		Mockito.when(this.dao.read(1L)).thenReturn(item);
		Mockito.when(this.utils.getString()).thenReturn("x");

		assertEquals(0, this.controller.delete());

		Mockito.verify(this.dao, Mockito.times(1)).readAll();
		Mockito.verify(this.utils, Mockito.times(1)).getLong();
		Mockito.verify(this.dao, Mockito.times(1)).read(1L);
		Mockito.verify(this.utils, Mockito.times(1)).getString();
		
	}
}

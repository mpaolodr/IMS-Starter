package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {

	private static final ItemDAO DAO = new ItemDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@BeforeClass
	public static void deleteAll() {
		for (Item item : DAO.readAll()) {
			DAO.delete(item.getId());
		}
	}

	@Test
	public void testReadAllEmptyDB() {
		assertEquals(new ArrayList<>(), DAO.readAll());
	}

	@Test
	public void testReadAll() {
		DAO.create(new Item("BioMutant", 59.99));
		List<Item> fromDB = DAO.readAll();
		List<Item> expected = new ArrayList<Item>();

		for (Item i : fromDB) {
			expected.add(new Item(i.getId(), "BioMutant", 59.99));
		}

		assertEquals(expected, DAO.readAll());

		for (Item i : fromDB) {
			DAO.delete(i.getId());
		}
	}

	@Test
	public void testReadEmptyDB() {
		assertNull(DAO.read(1L));
	}

	@Test
	public void testRead() {
		final Long ID = DAO.create(new Item("BioMutant", 59.99)).getId();
		assertEquals("BioMutant", DAO.read(ID).getName());

		DAO.delete(ID);
	}

	@Test
	public void testReadLatest() {

		Item item = DAO.create(new Item("BioMutant", 59.99));

		assertEquals(item, DAO.readLatest());

		DAO.delete(item.getId());
	}

	@Test
	public void testReadLatestEmptyDB() {
		assertNull(DAO.readLatest());
	}

	@Test
	public void testCreate() {

		Item item = DAO.create(new Item("BioMutant", 59.99));

		assertNotNull(DAO.read(item.getId()));

		DAO.delete(item.getId());

	}

	@Test
	public void testUpdateError() {
		assertNull(DAO.update(new Item(1L, "Test", 11.99), "all"));
	}

	@Test
	public void testUpdateName() {
		Item created = DAO.create(new Item("BioMutant", 59.99));
		DAO.update(new Item(created.getId(), "Borderlands", 59.99), "name");

		assertNotEquals(created.getName(), DAO.read(created.getId()).getName());

		DAO.delete(created.getId());
	}

	@Test
	public void testUpdatePrice() {
		Item created = DAO.create(new Item("BioMutant", 59.99));
		DAO.update(new Item(created.getId(), "BioMutant", 69.99), "price");

		assertNotEquals(created.getPrice(), DAO.read(created.getId()).getPrice());

		DAO.delete(created.getId());
	}

	@Test
	public void testUpdateAll() {
		Item created = DAO.create(new Item("BioMutant", 59.99));
		Item updated = DAO.update(new Item(created.getId(), "Borderlands", 11.99), "all");

		assertNotEquals(created, DAO.read(created.getId()));

		DAO.delete(created.getId());
	}
	
	@Test
	public void testUpdateInvalid() {
		Item created = DAO.create(new Item("BioMutant", 59.99));
		Item updated = DAO.update(new Item(created.getId(), "Borderlands", 11.99), "x");
		
		assertNull(updated);
		
		DAO.delete(created.getId());
	}

	@Test
	public void testDeleteEmptyDB() {
		assertEquals(0, DAO.delete(1));
	}

	@Test
	public void testDelete() {
		Item created = DAO.create(new Item("BioMutant", 59.99));
		assertEquals(1, DAO.delete(created.getId()));
	}

}

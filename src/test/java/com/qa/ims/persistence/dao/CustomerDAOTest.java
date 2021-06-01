package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAOTest {

	private static final CustomerDAO DAO = new CustomerDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@BeforeClass
	public static void deleteAll() {
		for (Customer c : DAO.readAll()) {
			DAO.delete(c.getId());
		}
	}

	@Test
	public void testCreateWithoutEmail() {
		final Customer created = new Customer("chris", "perrins", "Sanford, ME");
		final Customer fromDB = DAO.create(created);
		created.setId(fromDB.getId());

		assertEquals(created, fromDB);

		DAO.delete(fromDB.getId());
	}

	@Test
	public void testCreateWithEmail() {
		final Customer created = new Customer("chris", "perrins", "Sanford, ME", "test@doe.com");
		final Customer fromDB = DAO.create(created);
		created.setId(fromDB.getId());

		assertEquals(created, fromDB);

		DAO.delete(fromDB.getId());
	}
	
	@Test
	public void testCreateError() {
		final Customer created = new Customer("chris", "perrins", "Sanford, ME", "test@doe.com");
		final Customer created2 = new Customer("chris", "perrins", "Sanford, ME", "test@doe.com");
		final Customer fromDB = DAO.create(created);
		final Customer fromDB2 = DAO.create(created2);
		

		assertNull(fromDB2);

		DAO.delete(fromDB.getId());
	}

	@Test
	public void testReadAll() {

		DAO.create(new Customer("chris", "perrins", "Sanford, ME"));
		List<Customer> fromDB = DAO.readAll();
		List<Customer> expected = new ArrayList<Customer>();

		for (Customer c : fromDB) {
			expected.add(new Customer(c.getId(), "chris", "perrins", "Sanford, ME"));
		}

		assertEquals(expected, DAO.readAll());

		for (Customer c : fromDB) {
			DAO.delete(c.getId());
		}
	}

	@Test
	public void testReadAllEmptyDB() {

		List<Customer> fromDB = DAO.readAll();
		assertEquals(0, fromDB.size());

	}

	@Test
	public void testReadLatest() {
		DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));
		Customer latest = DAO.readLatest();

		assertEquals(new Customer(latest.getId(), "jordan", "harrison", "Sanford, ME"), DAO.readLatest());

		DAO.delete(latest.getId());
	}

	@Test
	public void testReadLatestEmptyDB() {
		assertNull(DAO.readLatest());
	}

	@Test
	public void testRead() {
		final Customer entry = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));

		assertEquals(entry.getFirstName() + entry.getSurname(),
				DAO.read(entry.getId()).getFirstName() + DAO.read(entry.getId()).getSurname());

		DAO.delete(entry.getId());
	}

	@Test
	public void testReadEmptyDB() {
		assertNull(DAO.read(1L));
	}

	@Test
	public void testUpdateEmptyDB() {
		assertNull(DAO.update(new Customer(1L, "Michael", "Jordan", "Sanford, ME"), "all"));
	}

	@Test
	public void testUpdateAll() {
		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));

		assertEquals("Michael",
				DAO.update(new Customer(created.getId(), "Michael", "Jordan", "Sanford, ME"), "all").getFirstName());

		DAO.delete(created.getId());
	}

	@Test
	public void testUpdateFirstname() {
		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));

		assertNotEquals("jordan",
				DAO.update(new Customer(created.getId(), "Michael", "harrison", "Sanford, ME"), "firstname")
						.getFirstName());

		DAO.delete(created.getId());
	}

	@Test
	public void testUpdateSurname() {
		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));

		assertNotEquals("harrison",
				DAO.update(new Customer(created.getId(), "jordan", "Michael", "Sanford, ME"), "surname").getSurname());

		DAO.delete(created.getId());
	}

	@Test
	public void testUpdateAddress() {
		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));

		assertNotEquals("Sanford, ME",
				DAO.update(new Customer(created.getId(), "jordan", "harrison", "Chicago, IL"), "address").getAddress());

		DAO.delete(created.getId());
	}

	@Test
	public void testUpdateEmail() {
		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));

		assertNotEquals("N/A", DAO
				.update(new Customer(created.getId(), "Michael", "harrison", "Sanford, ME", "new@email.com"), "email")
				.getEmail());

		DAO.delete(created.getId());
	}
	
	@Test
	public void testUpdateEmailError() {
		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME", "test@doe.com"));
		final Customer created2 = DAO.create(new Customer("jordan", "kilganon", "Sanford, ME", "new@email.com"));

		assertNull(DAO
				.update(new Customer(created.getId(), "jordan", "harrison", "Sanford, ME", "new@email.com"), "email"));

		DAO.delete(created.getId());
		DAO.delete(created2.getId());
	}

	@Test
	public void testDelete() {

		final Customer created = DAO.create(new Customer("jordan", "harrison", "Sanford, ME"));
		assertEquals(1, DAO.delete(created.getId()));
	}

	@Test
	public void testDeleteEmptyDB() {

		assertEquals(0, DAO.delete(1));
	}
}

package com.skilldistillery.bluepix.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class PageUserTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private PageUser pageUser;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("BluePixJPA");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		pageUser = em.find(PageUser.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		pageUser = null;
		em.close();
	}

	@Test
	void test_PageUser_mapping() {
		assertNotNull(em);
		assertEquals("admin", pageUser.getName());
	}

}

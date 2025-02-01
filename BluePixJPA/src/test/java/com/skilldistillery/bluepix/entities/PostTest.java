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

class PostTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Post post;

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
		post = em.find(Post.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		post = null;
		em.close();
	}

	@Test
	void test_Post_basic_mapping() {
		assertNotNull(post);
		assertEquals("Photos Printed?", post.getTitle());
	}
	@Test
	void test_Post_mapping_to_Comment() {
		assertNotNull(post.getPostComments());
		assertEquals("Bogos Binted?", post.getPostComments().getFirst().getComments());
		assertTrue(post.getPostComments().size() > 0);
	}

}

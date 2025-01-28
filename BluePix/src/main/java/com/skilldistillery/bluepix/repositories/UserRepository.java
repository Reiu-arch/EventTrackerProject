package com.skilldistillery.bluepix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.bluepix.entities.PageUser;

public interface UserRepository extends JpaRepository<PageUser, Integer> {

	PageUser findByNameAndPassword(String username, String password);

}

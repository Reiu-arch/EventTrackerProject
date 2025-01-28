package com.skilldistillery.bluepix.services;

import java.util.List;

import com.skilldistillery.bluepix.entities.PageUser;

public interface UserService {

	List<PageUser> findAll();
	PageUser findById(int userId);
	PageUser create(PageUser newUser);
	PageUser update(int userId, PageUser pageUser);
	boolean delete(int userId);
	PageUser authenticateUser(String username, String password);
}

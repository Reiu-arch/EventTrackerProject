package com.skilldistillery.bluepix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.bluepix.entities.PageUser;
import com.skilldistillery.bluepix.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public List<PageUser> findAll() {
		
		return userRepo.findAll();
	}

	@Override
	public PageUser findById(int userId) {
		Optional<PageUser> userOpt = userRepo.findById(userId);
		PageUser user = null;
		if(userOpt.isPresent()) {
			user = userOpt.get();
		}
		
		return user;
	}

	@Override
	public PageUser create(PageUser newUser) {
		return userRepo.saveAndFlush(newUser);
	}

	@Override
	public PageUser update(int userId, PageUser pageUser) {
		Optional<PageUser> userOpt = userRepo.findById(userId);
		PageUser updateUser = null;
		if(userOpt.isPresent()) {
			updateUser = userOpt.get();
			updateUser.setName(pageUser.getName());
			updateUser.setPassword(pageUser.getPassword());
			updateUser.setEmail(pageUser.getEmail());
			updateUser.setBiography(pageUser.getBiography());
			updateUser.setPictureUrl(pageUser.getPictureUrl());
			userRepo.saveAndFlush(updateUser);
		}
		return updateUser;
	}

	@Override
	public boolean delete(int userId) {
		boolean deleted = false;
		if(userRepo.existsById(userId)) {
			userRepo.deleteById(userId);
			deleted = true;
		}
		return deleted;
	}

	@Override
	public PageUser authenticateUser(String username, String password) {
		PageUser authenticatedUser = null;
		
		try {
			authenticatedUser = userRepo.findByNameAndPassword(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Invalid user :"+ username);
		}
		return authenticatedUser;
	}

}

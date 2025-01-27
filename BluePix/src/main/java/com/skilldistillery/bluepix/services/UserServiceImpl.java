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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int userId) {
		// TODO Auto-generated method stub
		return false;
	}

}

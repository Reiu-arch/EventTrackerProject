package com.skilldistillery.bluepix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.bluepix.entities.PageUser;
import com.skilldistillery.bluepix.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(path = {"users", "users/"})
	public List<PageUser> listUsers(){
		return userService.findAll();
	}

	@GetMapping(path = {"users/{userId}","users/{userId}/"})
	public PageUser findById(@PathVariable ("userId") int userId, HttpServletResponse resp){
		PageUser foundUser = userService.findById(userId);
		if(foundUser == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);//404
		}
		
		return foundUser;
	}
	@PostMapping(path = {"users", "users/"})
	public PageUser createUser(@RequestBody PageUser pageUser, HttpServletResponse resp, HttpServletRequest req) {
		try {
			pageUser = userService.create(pageUser);
			resp.setStatus(HttpServletResponse.SC_CREATED);//201
			resp.setHeader("Location", req.getRequestURL().append("/").append(pageUser.getId()).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400
			pageUser = null;
		}
		return pageUser;
	}
}
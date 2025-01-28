package com.skilldistillery.bluepix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.bluepix.entities.PageUser;
import com.skilldistillery.bluepix.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = { "users", "users/" })
	public List<PageUser> listUsers() {
		return userService.findAll();
	}

	@GetMapping(path = { "users/{userId}", "users/{userId}/" })
	public PageUser findById(@PathVariable("userId") int userId, HttpServletResponse resp) {
		PageUser foundUser = userService.findById(userId);
		if (foundUser == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);// 404
		}

		return foundUser;
	}

	@PostMapping(path = { "users", "users/" })
	public PageUser createUser(@RequestBody PageUser pageUser, HttpServletResponse resp, HttpServletRequest req) {
		try {
			pageUser = userService.create(pageUser);
			resp.setStatus(HttpServletResponse.SC_CREATED);// 201
			resp.setHeader("Location", req.getRequestURL().append("/").append(pageUser.getId()).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);// 400
			pageUser = null;
		}
		return pageUser;
	}

	@PutMapping(path = { "users/{userId}", "users/{userId}/" })
	public PageUser updateUser(@PathVariable("userId") int userId, @RequestBody PageUser newUserData,
			HttpServletResponse resp, HttpServletRequest req) {
		PageUser updateUser = null;

		try {
			updateUser = userService.update(userId, newUserData);
			if (newUserData == null) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);// 404
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		resp.setStatus(HttpServletResponse.SC_OK);// 200
		resp.setHeader("Location", req.getRequestURL().append("/").append(updateUser.getId()).toString());
		return updateUser;
	}

	@DeleteMapping(path = { "users/{userId}", "users/{userId}/" })
	public void delete(@PathVariable(name = "userId") Integer userId, HttpServletResponse resp) {
		try {
			boolean wasdeleted = userService.delete(userId);
			if (!wasdeleted) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);// 404
			}
			resp.setStatus(HttpServletResponse.SC_NO_CONTENT);//204
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);// 400
		}
	}
	
	//was not @RequestBody String name, @RequestBody String password
	@PostMapping("login")
	public PageUser login( @RequestAttribute(name = "name") String name, @RequestAttribute(name = "password") String password, HttpSession session, HttpServletResponse resp, HttpServletRequest req) {
		//session.getAtt doest seem to be able to get a specified field from the JSON POST request
		//How am i able to get raw attribute information from JSON for testing?
		
		
//		String name = (String) session.getAttribute("name");
//		String password = (String) session.getAttribute("password");
		PageUser user = userService.authenticateUser(name, password);
		
		
		//so far both the request body and getAtt dont seem to show up with anything
		System.out.println(user);
		System.out.println(name);
		System.out.println(password);
		try {
			if(user != null) {
				session.setAttribute("LoggedInUser", user);
				resp.setStatus(HttpServletResponse.SC_OK);//200
				resp.setHeader("Location", req.getRequestURL().append("users/").append(user.getId()).toString());//sends to their users/id
			}
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400
		}
		return user;
	}
}
package com.skilldistillery.bluepix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.bluepix.entities.PageUser;
import com.skilldistillery.bluepix.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@CrossOrigin({"*", "http://localhost/"})
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
	//sluthing around and i was able to find that you can load a dummy Object with the required information for a login (ie Uname & password)
	@PostMapping("login")
	public PageUser login(@RequestBody PageUser pageUser, HttpSession session, HttpServletResponse resp, HttpServletRequest req) {
		//session.getAtt doest seem to be able to get a specified field from the JSON POST request
		//How am i able to get raw attribute information from JSON for testing?
	    String name = pageUser.getName();
	    String password = pageUser.getPassword();
	    
	    //so far both the request body and getAtt dont seem to show up with anything
	    System.out.println("Received name: " + name);
	    System.out.println("Received password: " + password);
	    
	    PageUser user = userService.authenticateUser(name, password);

	    if (user != null) {
	        try {
	            // Store the user in session
	            session.setAttribute("LoggedInUser", user);
	            //so far this sysout is printing but my posting isnt working...
	            System.out.println("User logged in, stored in session: " + user);
	            
	            resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
	            resp.setHeader("Location", req.getRequestURL().append("users/").append(user.getId()).toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Req
	        }
	    } else {
	        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
	    }

	    return user;
	}
	
	
}
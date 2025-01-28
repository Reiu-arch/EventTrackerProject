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
import com.skilldistillery.bluepix.entities.Post;
import com.skilldistillery.bluepix.services.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@GetMapping(path = { "posts", "posts/" })
	public List<Post> listPosts() {
		return postService.findAll();
	}
	@GetMapping(path = { "posts/{postId}", "posts/{postId}/" })
	public Post findById(@PathVariable("postId") int postId, HttpServletResponse resp) {
		Post foundPost = postService.findById(postId);
		if (foundPost == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);// 404
		}

		return foundPost;
	}
	
	@PostMapping(path = { "posts", "posts/" })
	public Post createPost(@RequestBody Post post, HttpServletResponse resp, HttpServletRequest req, HttpSession session, PageUser user) {
		try {
			PageUser postingUser = (PageUser)session.getAttribute("LogginInUser");
			post = postService.create(post, 0);
			resp.setStatus(HttpServletResponse.SC_CREATED);// 201
			resp.setHeader("Location", req.getRequestURL().append("/").append(post.getId()).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);// 400
			post = null;
		}
		return post;
	}
}

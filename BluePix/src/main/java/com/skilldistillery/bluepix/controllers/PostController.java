package com.skilldistillery.bluepix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Post createPost(@RequestBody Post post, HttpServletResponse resp, HttpServletRequest req, HttpSession session) {
		try {
//			PageUser postingUser = (PageUser)session.getAttribute("LoggedInUser");
//			System.out.println("=======================================================");
//			System.out.println("=======================================================");
//			System.out.println(postingUser);
//			System.out.println("=======================================================");
//			System.out.println("=======================================================");
			//informed that session will no  longer be used, hard code a user for now and put through the creation. 
			//Anonymous user created for this purpose.
			post = postService.create(post, 4);
			resp.setStatus(HttpServletResponse.SC_CREATED);// 201
			resp.setHeader("Location", req.getRequestURL().append("/").append(post.getId()).toString());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);// 400
			post = null;
		}
		return post;
	}
	@PutMapping(path = { "posts/{postId}", "posts/{postId}/" })
	public Post updatePost(@PathVariable(name = "postId") int postId,@RequestBody Post updatePostData, HttpServletResponse resp) {
		Post updatePost = null;
		try {
			updatePost = postService.update(postId, updatePostData);
			if (updatePostData == null) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);// 404
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400
			
		}
		resp.setStatus(HttpServletResponse.SC_OK);//200
		return updatePost;
	}
	
	
	@DeleteMapping(path = { "posts/{postId}", "posts/{postId}/" })
	 public void delete(@PathVariable(name="postId")Integer postId, HttpServletResponse res) {
		 try {
			boolean wasdeleted = postService.delete(postId);
			 if(!wasdeleted) {
				 res.setStatus(HttpServletResponse.SC_NOT_FOUND);//404
			 }
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);//400
		}
	 }
}

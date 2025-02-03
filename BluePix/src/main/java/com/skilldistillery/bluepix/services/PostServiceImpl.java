package com.skilldistillery.bluepix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.bluepix.entities.PageUser;
import com.skilldistillery.bluepix.entities.Post;
import com.skilldistillery.bluepix.repositories.PostRepository;
import com.skilldistillery.bluepix.repositories.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public List<Post> findAll() {
		
		return postRepo.findAll();
	}

	@Override
	public Post findById(int userId) {
		Optional<Post> userOpt = postRepo.findById(userId);
		Post user = null;
		if(userOpt.isPresent()) {
			user = userOpt.get();
		}
		
		return user;
	}

	@Override
	public Post create(Post newPost, int userId) {
		PageUser user = userRepo.findById(userId).orElse(null);
		newPost.setPageUser(user);
		return postRepo.saveAndFlush(newPost);
	}

	@Override
	public Post update(int postId, Post postData) {
		Optional<Post> postOpt = postRepo.findById(postData.getId());
		Post updatePost = postOpt.get();
		if(updatePost == null) {
			return postData;
		}
		if(postData.getId() == updatePost.getId()) {
			updatePost.setTitle(postData.getTitle());
			updatePost.setDescription(postData.getDescription());
			updatePost.setImageUrl(postData.getImageUrl());
		}
		return updatePost;
	}

	@Override
	public boolean delete(int postId) {
		boolean deleted = false;
		if(postRepo.existsById(postId)) {
			postRepo.deleteById(postId);
			deleted = true;
		}
		return deleted;
	}
}

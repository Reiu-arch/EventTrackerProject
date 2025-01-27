package com.skilldistillery.bluepix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.bluepix.entities.Post;
import com.skilldistillery.bluepix.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

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
		return postRepo.saveAndFlush(newPost);
	}

	@Override
	public Post update(int postId, Post post, int userId) {
		Optional<Post> userOpt = postRepo.findById(postId);
		Post updateUser = null;
		if(userOpt.isPresent()) {
			
		}
		return updateUser;
	}

	@Override
	public boolean delete(int postId, int userId) {
		boolean deleted = false;
		if(postRepo.existsById(postId)) {
			postRepo.deleteById(postId);
			deleted = true;
		}
		return deleted;
	}
}

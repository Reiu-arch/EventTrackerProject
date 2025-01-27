package com.skilldistillery.bluepix.services;

import java.util.List;

import com.skilldistillery.bluepix.entities.Post;

public interface PostService {

	List<Post> findAll();
	Post findById(int postId);
	Post create(Post newPost, int userId);
	Post update(int postId, Post post, int userId);
	boolean delete(int postId, int userId);
}

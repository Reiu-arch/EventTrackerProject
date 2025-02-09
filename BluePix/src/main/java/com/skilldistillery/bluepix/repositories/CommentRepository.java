package com.skilldistillery.bluepix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.bluepix.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	List<Comment> findCommentsByPostId(int postId);
}

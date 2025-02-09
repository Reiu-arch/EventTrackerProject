package com.skilldistillery.bluepix.services;

import java.util.List;

import com.skilldistillery.bluepix.entities.Comment;


public interface CommentService {
	Comment findByCommentId(int commentId);
	List<Comment> findByPostId(int postId);
	Comment create(Comment newComment, int userId, Integer parentCommentId);
	Comment update(int commentId, Comment NewCommentData);
	boolean delete(int commentId);
}

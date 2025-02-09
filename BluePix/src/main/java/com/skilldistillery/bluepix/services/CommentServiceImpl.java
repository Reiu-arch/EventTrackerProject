package com.skilldistillery.bluepix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.bluepix.entities.Comment;
import com.skilldistillery.bluepix.entities.PageUser;
import com.skilldistillery.bluepix.repositories.CommentRepository;
import com.skilldistillery.bluepix.repositories.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Comment findByCommentId(int commentId) {
		return commentRepo.findById(commentId).orElse(null);
	}

	@Override
	public List<Comment> findByPostId(int postId) {
		return commentRepo.findCommentsByPostId(postId);
	}

	@Override
	public Comment create(Comment newComment, int userId, Integer parentCommentId) {
		PageUser user = userRepo.findById(userId).orElse(null);
		newComment.setUser(user);
		if(parentCommentId == 0) {
			parentCommentId = null;
		} else if (parentCommentId != 0) {
			Comment parent = commentRepo.findById(parentCommentId).orElse(null);
			newComment.setParentComment(parent);
		}
		return commentRepo.saveAndFlush(newComment);
	}

	@Override
	public Comment update(int commentId, Comment newCommentData) {
			Comment comment = commentRepo.findById(null).orElse(null);
			if(comment == null) {
				return newCommentData;
			} else if(newCommentData.getId() == comment.getId()) {
				comment.setComments(newCommentData.getComments());
				commentRepo.saveAndFlush(comment);
			}
		return comment;
	}

	@Override
	public boolean delete(int commentId) {
		boolean deleted = false;
		if(commentRepo.existsById(commentId)) {
			commentRepo.deleteById(commentId);
			deleted = true;
		}
		return deleted;
	}

	
	

}

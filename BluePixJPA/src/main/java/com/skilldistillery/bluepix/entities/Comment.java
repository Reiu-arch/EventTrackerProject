package com.skilldistillery.bluepix.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String comments;
	
	@Column(name = "create_date")
	@CreationTimestamp
	private LocalDateTime createDate;
	
	@Column(name = "last_update")
	@UpdateTimestamp
	private LocalDateTime lastUpdate;
	
	//connects the comment by the post Id
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	//creates an Id of the top most comment (I know it as the 
	//the parent comment)
	@ManyToOne
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;
	
	
	//gives a sub-comment an Id in the post_comment table and assigns 
	//it to a parent comment (unsure at the moment of what will occur when a sub comment 
	//is connected to another sub comment, will it accordion?
	@JsonIgnore
	@OneToMany(mappedBy = "parentComment")
	private List<Comment> subComments;
	
	
	//connects the comment to the user whom commented it
	@ManyToOne
	@JoinColumn(name="page_user_id")
	private PageUser user;
	

	public Comment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public List<Comment> getSubComments() {
		return subComments;
	}

	public void setSubComments(List<Comment> subComments) {
		this.subComments = subComments;
	}

	public PageUser getUser() {
		return user;
	}

	public void setUser(PageUser user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comments=" + comments + ", createDate=" + createDate + ", lastUpdate="
				+ lastUpdate + "]";
	}

	public Comment(int id, String comments, LocalDateTime createDate, LocalDateTime lastUpdate) {
		super();
		this.id = id;
		this.comments = comments;
		this.createDate = createDate;
		this.lastUpdate = lastUpdate;
	}
	
	

}

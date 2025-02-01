package com.skilldistillery.bluepix.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String description;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "create_date")
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "last_update")
	@UpdateTimestamp
	private LocalDateTime lastUpdate;
	
	@JsonIgnoreProperties({"posts"})
	@ManyToOne
	@JoinColumn(name="page_user_id")
	private PageUser pageUser;
	
	@OneToMany(mappedBy ="post")
	private List<Comment> PostComments;

	public Post() {
		super();
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public PageUser getPageUser() {
		return pageUser;
	}


	public void setPageUser(PageUser pageUser) {
		this.pageUser = pageUser;
	}

	public List<Comment> getPostComments() {
		return PostComments;
	}

	public void setPostComments(List<Comment> postComments) {
		PostComments = postComments;
	}


	public Post(int id, String title, String description, String imageUrl, LocalDateTime createdDate,
			LocalDateTime lastUpdate, PageUser pageUser) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
		this.createdDate = createdDate;
		this.lastUpdate = lastUpdate;
		this.pageUser = pageUser;
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
		Post other = (Post) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", imageUrl=" + imageUrl
				+ ", createdDate=" + createdDate + ", lastUpdate=" + lastUpdate + ", pageUser=" + pageUser
				+ ", PostComments=" + PostComments + "]";
	}
	
	
}

package com.skilldistillery.bluepix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.bluepix.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}

package com.blogappapis.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappapis.entities.Category;
import com.blogappapis.entities.Post;
import com.blogappapis.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByCategory(Category category);
	List<Post> findByUser(User user);
	List<Post> findByPostTitleContaining(String postName);
}

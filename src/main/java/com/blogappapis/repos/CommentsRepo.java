package com.blogappapis.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappapis.entities.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

}

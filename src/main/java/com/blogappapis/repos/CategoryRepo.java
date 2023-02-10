package com.blogappapis.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

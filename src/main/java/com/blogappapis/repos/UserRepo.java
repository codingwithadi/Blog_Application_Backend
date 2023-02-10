package com.blogappapis.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappapis.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}

package com.blogappapis.services;

import java.util.List;

import com.blogappapis.payloads.PostDTO;
import com.blogappapis.utilities.PagePostResponse;

public interface PostServices {

	// create post method
	PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId);

	// update post method
	PostDTO updatePost(PostDTO postDto, Integer postId);

	// delete post method
	void deletePost(Integer postId);

	// delete All -> in future update

	// get all post method
	PagePostResponse getAllPost(Integer pageNumber, Integer pageSize, String sort);

	// get single post;
	PostDTO getSinglePost(Integer postId);

	// get post by userId
	List<PostDTO> getPostByUser(Integer userId);

	// get post by category
	List<PostDTO> getPostByCategory(Integer categoryId);

	// get post by search
	List<PostDTO> getPostBySearchName(String keywords);

}

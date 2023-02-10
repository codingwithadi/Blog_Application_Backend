package com.blogappapis.services.implimentation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogappapis.entities.Category;
import com.blogappapis.entities.Post;
import com.blogappapis.entities.User;
import com.blogappapis.exceptions.ResourceNotFoundException;
import com.blogappapis.payloads.PostDTO;

import com.blogappapis.repos.CategoryRepo;
import com.blogappapis.repos.PostRepo;
import com.blogappapis.repos.UserRepo;
import com.blogappapis.services.PostServices;
import com.blogappapis.utilities.PagePostResponse;

@Service
public class PostServicesImpl implements PostServices {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	// create
	@Override
	public PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId) {
		// getting user from userId
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

		// getting category from categoryId
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

		// converting objects using model mapper.
		Post post = this.modelMapper.map(postDto, Post.class);

		// setting post field
		post.setPostImageName("Default.png");
		post.setPostAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		// save post
		Post createdPost = this.postRepo.save(post);

		// return to PostDTO class
		return this.modelMapper.map(createdPost, PostDTO.class);
	}

	// update
	@Override
	public PostDTO updatePost(PostDTO postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		// updating data
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImageName(postDto.getPostImageName());
		post.setPostAddedDate(new Date());

		Post updatedPost = this.postRepo.save(post);

		// convert data to PostDTO
		PostDTO postDtos = this.modelMapper.map(updatedPost, PostDTO.class);

		return postDtos;
	}

	// delete
	@Override
	public void deletePost(Integer postId) {
		// find post by id
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		// delete post

		this.postRepo.delete(post);
	}

	// getAll
	@Override
	public PagePostResponse getAllPost(Integer pageNumber, Integer pageSize, String sort) {

		// Sort.by(); is used for sorting posts.

		// Pageable object with pageRequest and of method
		Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(sort));

		// Page object for getting all post
		Page<Post> pagePost = this.postRepo.findAll(page);

		// get all method with getContent();
		List<Post> post = pagePost.getContent();

		// model mapping
		List<PostDTO> postDtos = post.stream().map((epost) -> this.modelMapper.map(epost, PostDTO.class))
				.collect(Collectors.toList());

		// creating PagePostResponse Obj.
		PagePostResponse pagePostResponse = new PagePostResponse();

		pagePostResponse.setContent(postDtos);
		pagePostResponse.setPageNumber(pagePost.getNumber());
		pagePostResponse.setPageSize(pagePost.getSize());
		pagePostResponse.setTotalElements(pagePost.getTotalElements());
		pagePostResponse.setTotalPages(pagePost.getTotalPages());
		pagePostResponse.setLastPage(pagePost.isLast());

		return pagePostResponse;
	}

	// getPost
	@Override
	public PostDTO getSinglePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		PostDTO postDto = this.modelMapper.map(post, PostDTO.class);
		return postDto;
	}

	// getByName
	@Override
	public List<PostDTO> getPostBySearchName(String keywords) {
		List<Post> posts = this.postRepo.findByPostTitleContaining(keywords);
		List<PostDTO> postDtos = posts.stream().map((epost) -> this.modelMapper.map(epost, PostDTO.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	// getByUser
	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		// get user by id
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

		// get All post using user
		List<Post> post = this.postRepo.findByUser(user);

		// model mapping
		List<PostDTO> postDtos = post.stream().map((epost) -> this.modelMapper.map(epost, PostDTO.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	// getByCategory
	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		// get category by id
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

		// get All post by category
		List<Post> post = this.postRepo.findByCategory(category);

		// model mapping
		List<PostDTO> postDtos = post.stream().map((epost) -> this.modelMapper.map(epost, PostDTO.class))
				.collect(Collectors.toList());

		return postDtos;
	}

}

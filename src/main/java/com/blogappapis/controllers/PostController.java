package com.blogappapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.blogappapis.payloads.PostDTO;
import com.blogappapis.services.FileServices;
import com.blogappapis.services.PostServices;
import com.blogappapis.utilities.ApiResponse;
import com.blogappapis.utilities.PagePostResponse;
import com.blogappapis.utilities.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostServices postServices;

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	private String path;

	// Post method for save Post
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDto, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId) {

		PostDTO savedPost = this.postServices.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDTO>(savedPost, HttpStatus.CREATED);
	}

	// Get method for getByUser post
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable("userId") Integer userId) {

		List<PostDTO> posts = this.postServices.getPostByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// Get method for getByCategory post
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {

		List<PostDTO> posts = this.postServices.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	// Get method for getAllPosts
	@GetMapping("/getAllPost")
	public ResponseEntity<PagePostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sort) {

		PagePostResponse allPosts = this.postServices.getAllPost(pageNumber, pageSize, sort);
		return new ResponseEntity<PagePostResponse>(allPosts, HttpStatus.OK);
	}

	// Get method for getPostByPostId
	@GetMapping("/getPost/{postId}")
	public ResponseEntity<PostDTO> getSinglePostById(@PathVariable("postId") Integer postId) {
		PostDTO post = this.postServices.getSinglePost(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}

	// Put method for Update Post
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDto,
			@PathVariable("postId") Integer postId) {
		PostDTO updatedPost = this.postServices.updatePost(postDto, postId);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	// Delete method for Delete post
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		this.postServices.deletePost(postId);

		// ApiResponse is a Utility class Which is provide responseMessages and
		// responseResults

		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully!!", true), HttpStatus.OK);
	}

	// get posts by search keywords
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDTO>> getPostBySearch(@PathVariable("keywords") String keywords) {
		List<PostDTO> postBySearchName = this.postServices.getPostBySearchName(keywords);
		return new ResponseEntity<List<PostDTO>>(postBySearchName, HttpStatus.OK);
	}

	// File upload by client
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadFile(@PathVariable("postId") Integer postId,
			@RequestParam("image") MultipartFile image) throws IOException {
		// get post first
		PostDTO post = this.postServices.getSinglePost(postId);

		// get fileName for saved in DB for particular user post
		String fileName = this.fileServices.uploadImage(path, image);

		// update post image name in DB;
		post.setPostImageName(fileName);
		// update post
		PostDTO updatedPost = this.postServices.updatePost(post, postId);

		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	// response for client
	@GetMapping("/post/image/download/{postImageName}")
	public void getFile(@PathVariable("postImageName") String postImageName, HttpServletResponse response)
			throws IOException {
		// filepath
		InputStream file = this.fileServices.getImage(path, postImageName);

		// set content type / media type
		response.setContentType(MediaType.IMAGE_PNG_VALUE);

		// forward stream to the client
		StreamUtils.copy(file, response.getOutputStream());
	}

}

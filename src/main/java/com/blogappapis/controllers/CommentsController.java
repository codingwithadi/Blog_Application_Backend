package com.blogappapis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blogappapis.payloads.CommentsDTO;
import com.blogappapis.services.CommentsServices;
import com.blogappapis.utilities.ApiResponse;

@RestController
@RequestMapping("api/")
@EnableMethodSecurity
public class CommentsController {

	@Autowired
	private CommentsServices commentServices;

	// Post Method
	@PostMapping("user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentsDTO> createComment(@RequestBody CommentsDTO commentsDto,
			@PathVariable("userId") int userId, @PathVariable("postId") int postId) {

		CommentsDTO comment = this.commentServices.createComment(commentsDto, userId, postId);
		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}

	// Update Comments method
	@PutMapping("/updateComment/{cmId}")
	public ResponseEntity<CommentsDTO> updateComment(@RequestBody CommentsDTO commentsDto,
			@PathVariable("cmId") int cmId) {
		CommentsDTO comment = this.commentServices.updateComment(commentsDto, cmId);
		return new ResponseEntity<CommentsDTO>(comment, HttpStatus.OK);
	}

	
	// Get All comments
	// Only Admin Role User can view All Comments
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllComments")
	public ResponseEntity<List<CommentsDTO>> getAllComments() {
		List<CommentsDTO> allComments = this.commentServices.getAllComment();
		return new ResponseEntity<List<CommentsDTO>>(allComments, HttpStatus.OK);
	}

	// Get comment by ID
	@GetMapping("/getComment/{cmId}")
	public ResponseEntity<CommentsDTO> getById(@PathVariable("cmId") int cmId) {
		CommentsDTO comment = this.commentServices.getCommentById(cmId);
		return new ResponseEntity<CommentsDTO>(comment, HttpStatus.OK);
	}

	// Delete Comment by id
	@DeleteMapping("/deleteComment/{cmId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("cmId") int cmId) {
		this.commentServices.deleteComment(cmId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully!!!", true), HttpStatus.OK);
	}

}

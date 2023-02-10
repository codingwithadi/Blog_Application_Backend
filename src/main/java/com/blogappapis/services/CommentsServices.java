package com.blogappapis.services;


import java.util.List;

import com.blogappapis.payloads.CommentsDTO;

public interface CommentsServices {

	//create
	CommentsDTO createComment(CommentsDTO commentsDto, int userId, int postId);
	
	//update
	CommentsDTO updateComment(CommentsDTO commentsDto, int cmId);
	
	//get all comments
	List<CommentsDTO> getAllComment();
	
	//get comment by userId
	CommentsDTO getCommentById(int cmId);
	
	//delete
	void deleteComment(int cmId);
}

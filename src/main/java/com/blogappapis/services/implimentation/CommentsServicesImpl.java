package com.blogappapis.services.implimentation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappapis.entities.Comments;
import com.blogappapis.entities.Post;
import com.blogappapis.entities.User;
import com.blogappapis.exceptions.ResourceNotFoundException;
import com.blogappapis.payloads.CommentsDTO;
import com.blogappapis.repos.CommentsRepo;
import com.blogappapis.repos.PostRepo;
import com.blogappapis.repos.UserRepo;
import com.blogappapis.services.CommentsServices;


@Service
public class CommentsServicesImpl implements CommentsServices {

	@Autowired
	private CommentsRepo commentsRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PostRepo postRepo;

	@Override
	public CommentsDTO createComment(CommentsDTO commentsDto, int userId, int postId) {

		// find user and post; when user or post not found then exception will hit;
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

		// Model Mapping
		Comments comment = this.modelMapper.map(commentsDto, Comments.class);

		// setting user and post in comments
		comment.setUser(user);
		comment.setPost(post);

		// save comments
		Comments savedComment = this.commentsRepo.save(comment);

		// return commentsDTO
		return this.modelMapper.map(savedComment, CommentsDTO.class);
	}

	@Override
	public CommentsDTO updateComment(CommentsDTO commentsDto, int cmId) {
		// get comment using cmId;
		Comments comment = this.commentsRepo.findById(cmId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", cmId));

		// updating value
		comment.setCmContent(commentsDto.getCmContent());

		// update value in DB
		Comments updatedComment = this.commentsRepo.save(comment);

		// mapping object in Dto
		CommentsDTO cm = this.modelMapper.map(updatedComment, CommentsDTO.class);

		return cm;
	}

	@Override
	public List<CommentsDTO> getAllComment() {
		// get all comments
		List<Comments> comments = this.commentsRepo.findAll();

		// each comment will mapped in dto class
		List<CommentsDTO> commentsDtos = comments.stream()
				.map((eComment) -> this.modelMapper.map(eComment, CommentsDTO.class)).collect(Collectors.toList());

		return commentsDtos;
	}

	@Override
	public CommentsDTO getCommentById(int cmId) {
		// get comment by Id
		Comments comment = this.commentsRepo.findById(cmId).orElseThrow(()-> new ResourceNotFoundException("Comment", "ID", cmId));
		
		//mapping in dto class
		CommentsDTO cmDto = this.modelMapper.map(comment, CommentsDTO.class);
		
		return cmDto;
	}

	@Override
	public void deleteComment(int cmId) {
		Comments comment = this.commentsRepo.findById(cmId).orElseThrow(()-> new ResourceNotFoundException("Comment", "ID", cmId));
		
		this.commentsRepo.delete(comment);

	}

}

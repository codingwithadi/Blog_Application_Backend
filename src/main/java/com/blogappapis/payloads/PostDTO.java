package com.blogappapis.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.blogappapis.entities.Category;
import com.blogappapis.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

	private Integer postId;

	@NotEmpty(message = "This is mandatory to fill the Title!!")
	@Size(min = 10, message = "It must be atleast 10 charactors!!")
	private String postTitle;

	@NotEmpty(message = "This is mandatory to fill the good Content!!")
	private String postContent;

	private String postImageName;
	private Date postAddedDate;

	private Category category;
	private User user;
	private List<CommentsDTO> comments = new ArrayList<>();

}

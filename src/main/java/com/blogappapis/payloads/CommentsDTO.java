package com.blogappapis.payloads;

import com.blogappapis.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDTO {

	private int cmId;
	private String cmContent;
	
	private User user;
}

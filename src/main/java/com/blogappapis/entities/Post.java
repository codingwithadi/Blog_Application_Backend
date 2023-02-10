package com.blogappapis.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	private String postTitle;
	private String postContent;
	private String postImageName;
	private Date postAddedDate;

	// Many post one user; ManyToOne relationship;
	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	// Many post one category' ManyToOne relationship;
	@ManyToOne
	@JoinColumn(name = "categoryID")
	private Category category;

	// One post contains many comments
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comments> comments = new ArrayList<>();

}

package com.blogappapis.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blogappapis.entities.Comments;
import com.blogappapis.entities.Post;
import com.blogappapis.entities.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private int user_Id;

	@NotEmpty
	@Size(min = 2, message = "User Name is must and it should be minimum 2 charactors.!!")
	private String userName;

	@Email(message = "Email ID is not Valid. please write your Email ID correctly.!!")
	private String userEmailId;

	@NotEmpty
	@Size(min = 4, max = 8, message = "Password should be minimum 4 charactors.!!")
	private String userPassword;

	@NotNull
	private String userAbout;

	// one user has many post;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> post = new ArrayList<>();

	// one user has many comments
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comments> comments = new ArrayList<>();

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "user_Id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
	private Set<Role> role = new HashSet<>();

}

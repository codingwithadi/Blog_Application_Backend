package com.blogappapis.payloads;

import java.util.ArrayList;
import java.util.List;

import com.blogappapis.entities.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	private Integer categoryId;

	@NotEmpty
	@Size(min = 2, message = "Title must be atleast two charators!!")
	private String categoryTitle;

	@NotEmpty
	@Size(min = 10, message = "Description is must and it should be atleast size 10!!")
	private String categoryDescription;

	// one category contains many post;
	@OneToMany(mappedBy = "category", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post> post = new ArrayList<>();

}

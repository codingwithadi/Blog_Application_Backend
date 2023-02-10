package com.blogappapis.utilities;

import java.util.List;

import com.blogappapis.payloads.PostDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagePostResponse {

	// page information for client/User

	private List<PostDTO> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean isLastPage;

}

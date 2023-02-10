package com.blogappapis.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private String resourceName;
	private String resourceFieldName;
	private long resourceId;
	
	public ResourceNotFoundException(String resourceName, String resourceFieldName, long resourceId) {
		super(String.format("Oops!! %s not found in system with %s : %s", resourceName, resourceFieldName, resourceId));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.resourceId = resourceId;
	}
	
	
}

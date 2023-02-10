package com.blogappapis.services;

import java.util.List;

import com.blogappapis.payloads.CategoryDTO;

public interface CategoryServices {
	
	//createCategory
	CategoryDTO createCategory(CategoryDTO categoryDto);
	
	//updateCategory
	CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId);
	
	//getCategory
	CategoryDTO getSingleCategory(Integer categoryId);
	
	//getAllCategory
	List<CategoryDTO> getAllCategory();
	
	//deleteCategory
	void deleteCategory(Integer categoryId);

}

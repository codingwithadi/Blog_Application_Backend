package com.blogappapis.services.implimentation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappapis.entities.Category;
import com.blogappapis.exceptions.ResourceNotFoundException;
import com.blogappapis.payloads.CategoryDTO;
import com.blogappapis.repos.CategoryRepo;
import com.blogappapis.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices {
	
	//Category Repo
	@Autowired
	private CategoryRepo categoryRepo;
	
	//Model Mapper
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		
		//first categoryDto convert into category using modelMapper.
		Category category = this.modelMapper.map(categoryDto, Category.class);
		
		//then using springData JPA we save this Category.
		Category createdCategory = this.categoryRepo.save(category);
		
		//Again we convert existing category to categoryDTO.
		return this.modelMapper.map(createdCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId) {
		
		//category find using cateogryId. if id is not found then throw exception.
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
		//updating data take from user and update existing category.
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		//save updated category.
		Category updatedCategory = this.categoryRepo.save(category);
		
		//converting category to categoryDto and return.
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getSingleCategory(Integer categoryId) {
		
		//get single category.
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
		//return converted category to cateoryDto.
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		//get all category in list
		List<Category> categories = this.categoryRepo.findAll();
		
		//convert each data to DTO using stream api
		List<CategoryDTO> categoryDto = categories.stream().map((category)-> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		//delete category using categoryId
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepo.delete(category);
	}

}

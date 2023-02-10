package com.blogappapis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogappapis.payloads.CategoryDTO;
import com.blogappapis.services.CategoryServices;
import com.blogappapis.utilities.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	// category service
	@Autowired
	private CategoryServices categoryService;

	// Post method for save data
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		CategoryDTO createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
	}

	// Put method for update
	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto,
			@PathVariable("categoryId") Integer categoryId) {
		CategoryDTO updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updateCategory);
	}

	// Get method for single category
	@GetMapping("/getCategory/{categoryId}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable("categoryId") Integer categoryId) {
		return ResponseEntity.ok(this.categoryService.getSingleCategory(categoryId));
	}

	// Get method for All category
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<CategoryDTO>> getAllCategory() {
		List<CategoryDTO> categories = this.categoryService.getAllCategory();
		return ResponseEntity.ok(categories);
	}

	// Delete method
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable("categoryId") Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		
		// ApiResponse is a Utility class Which is provide responseMessages and
		// responseResults
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully!!", true), HttpStatus.OK);
	}

	// Delete All method

}

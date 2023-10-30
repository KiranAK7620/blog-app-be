package com.blogapp.services;

import java.util.List;

import com.blogapp.payloads.CategoryDto;

public interface CategoryService {
	
	//create
		 CategoryDto createCategory(CategoryDto categoryDto);
		
	//update
		 CategoryDto updateCategory(CategoryDto categoryDto , Integer categotyId);
		 
	//delete
		 void deleteCategory(Integer categoryId);
		 
	//get
		 CategoryDto getCategory(Integer categoryId);
		 
	//get All
		 List<CategoryDto> getCategories();
}

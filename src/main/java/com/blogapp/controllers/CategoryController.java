package com.blogapp.controllers;

import java.util.List;

import jakarta.validation.Valid;

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

import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;
import com.blogapp.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategory=this.categoryService.createCategory(categoryDto);

		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,@PathVariable Integer categoryId){
		
		CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully!!", true),HttpStatus.OK);
	}	
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
		
	CategoryDto getCategory=this.categoryService.getCategory(categoryId);
		
		return new ResponseEntity<CategoryDto>(getCategory,HttpStatus.OK);
	}
	
	//get All
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
	List<CategoryDto> allCategories=this.categoryService.getCategories();
		
//		return new ResponseEntity<List<CategoryDto>>(allCategories,HttpStatus.OK);
		return ResponseEntity.ok(allCategories);
	}
	
}

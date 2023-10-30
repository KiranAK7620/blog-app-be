package com.blogapp.services;

import java.util.List;

import com.blogapp.entities.Post;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

public interface PostService {

	//create
		PostDto createPost(PostDto postDto ,Integer userId , Integer categoryId);
	//update
		PostDto updatePost(PostDto postDto , Integer postId);
	
	//delete
		void deletePost(Integer postId);
		
	//get
		PostDto getPostById(Integer postId);
		
	//get All
		PostResponse getAllPosts(Integer pageNumber , Integer pageSize , String sortBy ,String sortDir);
		
	//get All Posts By category
		PostResponse getPostsByCategory(Integer pageNumber , Integer pageSize,Integer categoryId);
		
	//get All Posts By user
		PostResponse getPostsByUser(Integer pageNumber , Integer pageSize ,Integer userId);
		
	//Search Posts
		List<PostDto> searchPosts(String keyword);
		
}

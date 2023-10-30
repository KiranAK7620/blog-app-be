package com.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.PostService;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	//create
	@Override
	public PostDto createPost(PostDto postDto, Integer userId ,Integer categoryId ) {
		
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","User id",userId));
		
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
	
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
			
		return this.modelMapper.map(newPost , PostDto.class);
	}

	//update
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
//		post.setAddedDate(post.getAddedDate());
//		post.setCategory(postDto.getCategory());
//		post.setUser(postDto.getUser());
		Post updatedPost=this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	//delete
	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post id", postId));
		this.postRepo.delete(post);
	}

	
	//get by id
	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post","Post id",postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	//get all
	@Override
	public PostResponse getAllPosts(Integer pageNumber , Integer pageSize ,String sortBy , String sortDir) {
		
//		int pageSize=5;
//		int pageNumber=1;
//		Pageable pageable=PageRequest.of(pageNumber, pageSize);
//		Pageable pageable=PageRequest.of(pageNumber, pageSize , Sort.by(sortBy));
//		Pageable pageable=PageRequest.of(pageNumber, pageSize , Sort.by(sortBy).descending());
		
//		Sort sort = null ;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort=Sort.by(sortBy).descending();
//		}
		Sort sort =sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable=PageRequest.of(pageNumber, pageSize ,sort );
				
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> posts=pagePost.getContent();

		List<PostDto> allPosts=posts.stream().map((cat)->this.modelMapper.map(cat,PostDto.class)).collect(Collectors.toList());
			
		PostResponse postResponse =new PostResponse();
		
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	//get by category
	@Override
	public PostResponse getPostsByCategory(Integer pageNumber , Integer pageSize,  Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category" , "Category id",categoryId));
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost=this.postRepo.findByCategory(cat , pageable);
		List<Post> posts=pagePost.getContent();
		
		List<PostDto> allPosts=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse =new PostResponse();
		
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	//get by user
	@Override
	public PostResponse getPostsByUser(Integer pageNumber , Integer pageSize ,Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User" , "User id",userId));
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		
		Page<Post> pagePost=this.postRepo.findByUser(user, pageable);
		List<Post> posts=pagePost.getContent();			
		
		List<PostDto> allPosts=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse =new PostResponse();
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	//search
	@Override
	public List<PostDto> searchPosts(String keyword) {
		
//		List<Post> posts= this.postRepo.findByTitleContaining(keyword);
		List<Post> posts= this.postRepo.searchByTitle("%"+keyword+"%");
		
		List<PostDto>	allPosts= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	
		return allPosts;
	}

}

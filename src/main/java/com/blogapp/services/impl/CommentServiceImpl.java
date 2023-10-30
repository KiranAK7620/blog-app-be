package com.blogapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "Post id", postId));
		
//		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ", "User id", userId));

		Comment comment	= this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
//		comment.setUser(user);
		
		Comment savedComment = this.commentRepo.save(comment); 
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment= this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment ", "Comment id", commentId));
	
		this.commentRepo.delete(comment);
	
	}

}

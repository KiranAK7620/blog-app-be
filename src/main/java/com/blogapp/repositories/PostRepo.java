package com.blogapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	Page<Post> findByUser(User user, Pageable pageable);
	
	Page<Post> findByCategory(Category category, Pageable pageable);
	
	List<Post> findByTitleContaining(String title);
	
	@Query("Select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
}

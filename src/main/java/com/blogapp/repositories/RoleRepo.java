package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

	
}

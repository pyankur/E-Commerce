package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	 Users findByEmail(String email);
}

package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Bufcart;

public interface BufCartRepository extends JpaRepository<Bufcart, Long>{

}

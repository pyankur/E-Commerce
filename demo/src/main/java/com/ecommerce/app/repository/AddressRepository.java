package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

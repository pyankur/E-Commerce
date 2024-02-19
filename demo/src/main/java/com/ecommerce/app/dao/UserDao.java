package com.ecommerce.app.dao;

import java.util.List;
import java.util.Optional;

import com.ecommerce.app.model.Address;
import com.ecommerce.app.model.Bufcart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.Users;

public interface UserDao {

	public void addUser(Users user);
	public List<Users> getAllUsers();
	public Users findUserBy(String email);
	public List<Product> getAllProduct();
	public void addProduct(Product product);
	public void addAddress(Address addr,String email);
	public Product getProduct(Long prodId);
	public void addCart(Bufcart buf);
	public List<Bufcart> getAllCart(String email);
}

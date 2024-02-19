package com.ecommerce.app.dao;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.model.Address;
import com.ecommerce.app.model.Bufcart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.Users;
import com.ecommerce.app.repository.AddressRepository;
import com.ecommerce.app.repository.BufCartRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.util.PasswordUtil;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProductRepository productRepo; 
	@Autowired
	private AddressRepository addressrepo;
	@Autowired
	private BufCartRepository bufrepo;
	
	@Override
	public void addUser(Users user) {
		PasswordUtil putil = new PasswordUtil();
		String salt=putil.getSalt();
		System.out.println("User dao"+user);
		try {
			Random ran=new Random();
			String newPass=putil.hashAndSaltPassword(user.getPassword(), salt);
			user.setPassword(newPass);
			user.setSalt(salt);
//			user.setId(Long.valueOf(ran.nextInt(1000)));
			userRepo.save(user);
		}catch (NoSuchAlgorithmException  e) {
			// TODO: handle exception
			System.out.println("Exception ocurred...");
		}
		
		
	}
	 public List<Users> getAllUsers() {
	        List<Users> users = null; // Initialize the variable

	        try {
	            users = userRepo.findAll();
	        } catch (Exception e) {
	            // Handle exception appropriately, e.g., log it
	            e.printStackTrace();
	        }

	        return users; // Return the list of users
	    }
	 public Users findUserBy(String email) {
		 Users user=null;
		 try {
			user=userRepo.findByEmail(email);
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		 return user;
	 }
	 public void addProduct(Product product) {
		 try {
			 productRepo.save(product);
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	 }
	 public List<Product> getAllProduct() {
	        List<Product> products = null; // Initialize the variable

	        try {
	        	products = productRepo.findAll();
	        } catch (Exception e) {
	            // Handle exception appropriately, e.g., log it
	            e.printStackTrace();
	        }

	        return products; // Return the list of users
	    }
	 
	 @Override
	public void addAddress(Address addr,String email) {
		try {
			addr.setEmail(email);
			addressrepo.save(addr);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	 public Product getProduct(Long prodId) {
		 Product product = null; // Initialize the variable

	        try {
	        	product= productRepo.findById(prodId).get();
	        } catch (Exception e) {
	            // Handle exception appropriately, e.g., log it
	            e.printStackTrace();
	        }

	        return product; // Return the list of users
	  }
	 public void addCart(Bufcart buf) {
		 try {
			 bufrepo.save(buf);
		 }catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
	 }
	 public List<Bufcart> getAllCart(String email) {
	        List<Bufcart> bufCart = null; // Initialize the variable
	        List<Bufcart> items=new ArrayList<>();
	        try {
	        	bufCart = bufrepo.findAll();
	        	for(Bufcart cart:bufCart) {
	        		System.out.println(cart);
	        		if(cart.getEmail().equals(email)) {
	        			items.add(cart);
	        		}
	        	}
	        } catch (Exception e) {
	            // Handle exception appropriately, e.g., log it
	            e.printStackTrace();
	        }

	        return items; // Return the list of users
	 }
}

package com.ecommerce.app.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.app.dao.UserDao;
import com.ecommerce.app.model.Address;
import com.ecommerce.app.model.Bufcart;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.model.Users;
import com.ecommerce.app.util.PasswordUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
@SessionAttributes("useremail")
public class HomeController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView viewLogin() {
		ModelAndView mv=new ModelAndView("login");
		mv.addObject("userloginform", new Users());
		return mv;
	}
		
	@RequestMapping(value = "/loginCheck",method = RequestMethod.POST)
	public ModelAndView validateLogin(@ModelAttribute("userloginform") Users user,HttpSession session) throws NoSuchAlgorithmException{
		List<Users> allUser=userDao.getAllUsers();
		System.out.println(allUser);
		boolean userFlag=false,adminFlag=false;
		for(int i=0;i<allUser.size();i++) {
			if(allUser.get(i).getEmail().equals(user.getEmail()) && allUser.get(i).getRole().equals("user")) {
				userFlag=true;
			}else if(allUser.get(i).getEmail().equals(user.getEmail()) && allUser.get(i).getRole().equals("admin")) {
				adminFlag=true;
			}
		}
		if(userFlag) {
			Users role_user=userDao.findUserBy(user.getEmail());
			if(PasswordUtil.hashAndSaltPassword(user.getPassword(), role_user.getSalt()).equals(role_user.getPassword())) {
				List<Product> allProduct=userDao.getAllProduct();
				ModelAndView model=new ModelAndView("userPage");
				model.addObject("listProduct", allProduct);
				model.addObject("useremail", user.getEmail());
				session.setAttribute("user", user.getEmail());
				return model;				
			}
		}else if(adminFlag) {
			Users role_admin=userDao.findUserBy(user.getEmail());
			if(PasswordUtil.hashAndSaltPassword(user.getPassword(), role_admin.getSalt()).equals(role_admin.getPassword())) {
				List<Product> allProduct=userDao.getAllProduct();
				ModelAndView model=new ModelAndView("adminPage");
				model.addObject("listProduct", allProduct);
				model.addObject("useremail", user.getEmail());
				session.setAttribute("user", user.getEmail());
				return model;				
			}
		}
		return new ModelAndView(new RedirectView("login"));
	}
	@RequestMapping(value = "/home")
	public ModelAndView homeDirect(HttpSession session) throws NoSuchAlgorithmException {

		ModelAndView model = new ModelAndView("userPage");
		List<Product> list = userDao.getAllProduct();

		model.addObject("useremail", session.getAttribute("useremail"));
		model.addObject("listProduct", list);
		session.setAttribute("user", session.getAttribute("useremail"));
		return model;

	}

	
	@RequestMapping(value = "/signUp",method = RequestMethod.GET)
	public ModelAndView signUp() {
		ModelAndView model=new ModelAndView("signup");
		model.addObject("usersignform",new Users());
		return model;
	}
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("usersignform") Users user) {
		ModelAndView model=new ModelAndView(new RedirectView("login"));
		System.out.println(user);
		try {		
			userDao.addUser(user);
			model.addObject("errMsg", "User Registered successfully");
		}catch (Exception e) {
			// TODO: handle exception
		}		
		return model;
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.asMap().remove("useremail");
        return "redirect:/user/login";
    }
	@RequestMapping(value = "/adminhome")
	public ModelAndView homeDirectAdmin(HttpSession session) throws NoSuchAlgorithmException {

		List<Product> allProduct=userDao.getAllProduct();
		ModelAndView model=new ModelAndView("adminPage");
		model.addObject("listProduct", allProduct);
		session.setAttribute("useremail", session.getAttribute("user"));
		return model;
	}
	
	@RequestMapping(value = "/viewProduct",method = RequestMethod.GET)
	public ModelAndView viewProduct() {
		ModelAndView model=new ModelAndView("addItem");
		model.addObject("productform", new Product());
		return model;
	}
	
	@RequestMapping(value = "/addProduct",method = RequestMethod.POST)
	public ModelAndView addProduct(@ModelAttribute("productform") Product product) {
		Random random=new Random();
		product.setProductId(Integer.toString(random.nextInt(1000)));
		userDao.addProduct(product);
		return new ModelAndView("redirect:/user/adminhome");
	}
	
	@RequestMapping(value = "/address",method = RequestMethod.GET)
	public ModelAndView viewAddress(HttpSession session) {
		ModelAndView model=new ModelAndView("address");
		model.addObject("addressform", new Address());
		model.addObject("useremail", session.getAttribute("user"));
		model.addObject("useremail", session.getAttribute("useremail"));
		return model;
	}
	
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public ModelAndView addressChange(@ModelAttribute("addressform") Address addr,HttpSession session) {
		String email=String.valueOf(session.getAttribute("useremail"));
		System.out.println("Address..............."+email);
		userDao.addAddress(addr,email);
		return new ModelAndView("redirect:/user/home");
	}
	
	// ==================ADDING PRODUCT TO
		// CART==============================================//
	@RequestMapping(value = "/product/{prodId}", method = RequestMethod.GET)
	public ModelAndView addtocart(@PathVariable("prodId") Long prodId, HttpSession session) {
		
		ModelAndView model=new ModelAndView("userPage");
		List<Product> list=userDao.getAllProduct();
		Product prod=userDao.getProduct(prodId);
		Bufcart buf = new Bufcart();
		buf.setBufcartId(Integer.toString(new Random().nextInt(1000)));
		buf.setProductId(String.valueOf(prodId));
		buf.setProductName(prod.getProductname());
		buf.setPrice(prod.getProductprice());
		buf.setEmail(String.valueOf(session.getAttribute("useremail")));
		buf.setOrderId(0);
		buf.setQuantity(String.valueOf(1));
		userDao.addCart(buf);
		model.addObject("useremail", session.getAttribute("useremail"));
		model.addObject("listProduct", list);
		return model;
	}
	
	@RequestMapping(value = "/viewcart", method = RequestMethod.GET)
	public ModelAndView viewCart(HttpSession session) {

		ModelAndView model = new ModelAndView("viewcart");
		String email = (String) session.getAttribute("useremail");
		List<Bufcart> list = userDao.getAllCart(email);
		model.addObject("listBufcart", list);
		return model;

	}

	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

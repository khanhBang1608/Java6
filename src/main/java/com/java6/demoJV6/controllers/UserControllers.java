package com.java6.demoJV6.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllers {
	@GetMapping("/")
	public String index(Model model) {
		return "user/index.html";
	}
	
	@GetMapping("/register/otp")
	public String registerOtp(Model model) {
		return "user/otp-form.html";
	}

	@GetMapping("/otp-form")
	public String register(Model model) {
		return "user/register.html";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword(Model model) {
		return "user/ForgotPassword.html";
	}

	@GetMapping("/about")
	public String about(Model model) {
		return "user/introduce.html";
	}

	@GetMapping("/present")
	public String present(Model model) {
		return "user/present.html";
	}

	@GetMapping("/product")
	public String product(Model model) {
		return "user/product.html";
	}

	@GetMapping("/product/detail")
	public String productDetail(Model model) {
		return "user/product-detail.html";
	}

	@GetMapping("/user/cart")
	public String userCart(Model model) {
		return "user/cart.html";
	}

	@GetMapping("/user/favorite")
	public String userFavorite(Model model) {
		return "user/favorite.html";
	}

	@GetMapping("/user/profile")
	public String userProfile(Model model) {
		return "user/profile.html";
	}

	@GetMapping("/user/change-password")
	public String userChange_password(Model model) {
		return "user/ChangePassword.html";
	}

	@GetMapping("/user/address")
	public String userAddress(Model model) {
		return "user/address_list.html";
	}

	@GetMapping("/user/address/add")
	public String userAddressAdd(Model model) {
		return "user/address_form.html";
	}

	@GetMapping("/user/order/history")
	public String userOrderHistory(Model model) {
		return "user/order_history.html";
	}

	@GetMapping("/user/order/details")
	public String userOrderDetail(Model model) {
		return "user/order_history_detail.html";
	}
	
}

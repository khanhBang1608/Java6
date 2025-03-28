package com.java6.demoJV6.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminControllers {

	@GetMapping("/admin")
	public String admin(Model model) {
		return "admin/manage_dashboard.html";
	}

	@GetMapping("/admin/product")
	public String adminProduct(Model model) {
		return "admin/manage_product.html";
	}

	@GetMapping("/admin/product/image")
	public String adminProductImage(Model model) {
		return "admin/manage_image.html";
	}

	@GetMapping("/admin/category")
	public String adminCategory(Model model) {
		return "admin/manage_category.html";
	}

	@GetMapping("/admin/orde")
	public String adminOrder(Model model) {
		return "admin/manage_order.html";
	}

	@GetMapping("/admin/order/detail")
	public String adminOrderDetail(Model model) {
		return "admin/manage_orderDetail.html";
	}

	@GetMapping("/admin/user")
	public String adminUser(Model model) {
		return "admin/manage_user.html";
	}
}

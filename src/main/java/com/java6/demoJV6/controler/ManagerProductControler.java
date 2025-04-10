package com.java6.demoJV6.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ManagerProductControler {
	@GetMapping("/")
	public String form() {
		return "";
	}
	
	@PostMapping("/form")
	public String addProduct() {
		return "";
	}
	
	
}

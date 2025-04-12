package com.java6.demoJV6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.bean.RegisterBean;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterBean registerBean, BindingResult result) {
    	System.out.println(registerBean);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Dữ liệu không hợp lệ.");
        }

        if (!registerBean.getPassword().equals(registerBean.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu và xác nhận không khớp.");
        }

        if (userService.isEmailExists(registerBean.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã được sử dụng.");
        }

        UserEntity user = userService.registerUser(registerBean);
        return ResponseEntity.ok().build();
    }
}
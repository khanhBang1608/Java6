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
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute RegisterBean registerBean, BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        // Lỗi validate từ annotation
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!registerBean.getPassword().equals(registerBean.getConfirmPassword())) {
            errors.put("confirmPassword", "Mật khẩu và xác nhận không khớp.");
        }

        // Kiểm tra email trùng
        if (userService.isEmailExists(registerBean.getEmail())) {
            errors.put("email", "Email đã được sử dụng.");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        userService.registerUser(registerBean);
        return ResponseEntity.ok().build();
    }

}

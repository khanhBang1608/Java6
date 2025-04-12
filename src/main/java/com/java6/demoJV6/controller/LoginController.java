package com.java6.demoJV6.controller;

import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.utils.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        return userService.findByEmail(email).map(user -> {
            String hashedPassword = PasswordUtil.hashPassword(password);
            if (user.getPassword().equals(hashedPassword)) {
                return ResponseEntity.ok(user); // Trả về user nếu đúng mật khẩu
            } else {
                return ResponseEntity.badRequest().body("Sai mật khẩu");
            }
        }).orElse(ResponseEntity.badRequest().body("Email không tồn tại"));
    }
}

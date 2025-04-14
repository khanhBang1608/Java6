package com.java6.demoJV6.controller;

import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.utils.PasswordUtil;
import com.java6.demoJV6.dto.LoginResponseDTO;
import com.java6.demoJV6.dto.UserDTO;

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
    public ResponseEntity<?> login(@RequestParam("email") String email,
                                   @RequestParam("password") String password) {
        return userService.findByEmail(email).map(user -> {
            if (!user.getStatus()) {
                return ResponseEntity.badRequest().body("Tài khoản của bạn đã bị khóa.");
            }

            String hashedPassword = PasswordUtil.hashPassword(password);
            if (user.getPassword().equals(hashedPassword)) {
                String token = "mock-token-" + user.getId(); // tạm thời (hoặc dùng JWT)

                // Convert to DTO
                UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAvatar(),
                    user.getStatus(),
                    user.getRole()
                );

                return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
            } else {
                return ResponseEntity.badRequest().body("Sai mật khẩu");
            }
        }).orElse(ResponseEntity.badRequest().body("Email không tồn tại"));
    }

}

package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.LoginResponseDTO;
import com.java6.demoJV6.dto.UserDTO;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.utils.PasswordUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Chỉ giữ cái này nếu Vue chạy tại 5173
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletResponse response) {

        return userService.findByEmail(email).map(user -> {

            if (!user.getStatus()) {
                return ResponseEntity.badRequest().body("Tài khoản của bạn đã bị khóa.");
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            if (user.getPassword().equals(hashedPassword)) {
                // Tạo token tạm thời (nên dùng JWT nếu cần)
                String token = "mock-token-" + user.getId();

                // Tạo cookie
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60); // 1 ngày
                response.addCookie(cookie);

                // Convert sang DTO
                UserDTO userDTO = new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAvatar(),
                        user.getStatus(),
                        user.getRole());

                // Trả về response chứa token và thông tin người dùng
                return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));

            } else {
                return ResponseEntity.badRequest().body("Đăng nhập Không thành công.!");
            }

        }).orElse(ResponseEntity.badRequest().body("Email không tồn tại"));
    }
}

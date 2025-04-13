package com.java6.demoJV6.controller;

import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.utils.PasswordUtil;
import com.java6.demoJV6.entity.UserEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletResponse response) {

        return userService.findByEmail(email).map(user -> {
            String hashedPassword = PasswordUtil.hashPassword(password);

            if (user.getPassword().equals(hashedPassword)) {
                // Tạo cookie
                String token = "dummy-token"; // Nếu bạn dùng JWT thì thay ở đây
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60); // 1 ngày

                response.addCookie(cookie);

                // Tạo response JSON
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getUserId());
                userInfo.put("email", user.getEmail());
                userInfo.put("fullname", user.getName());

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("token", token);     // 👈 Đúng định dạng Vue cần
                responseMap.put("user", userInfo);   // 👈 Đúng định dạng Vue cần

                return ResponseEntity.ok(responseMap);
            } else {
                return ResponseEntity.badRequest().body("Sai mật khẩu");
            }
        }).orElse(ResponseEntity.badRequest().body("Email không tồn tại"));
    }
}

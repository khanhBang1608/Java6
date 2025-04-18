package com.java6.demoJV6.controller;

import com.java6.demoJV6.component.JwtUtil;
import com.java6.demoJV6.dto.LoginResponseDTO;
import com.java6.demoJV6.dto.UserDTO;
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

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> login(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   HttpServletResponse response) {

        return userService.findByEmail(email).map(user -> {
            if (!user.getStatus()) {
                return ResponseEntity.badRequest().body("Tài khoản đã bị khóa");
            }

            if (user.getPassword().equals(PasswordUtil.hashPassword(password))) {
                String token = jwtUtil.generateToken(user.getId(), user.getRole());

                UserDTO userDTO = new UserDTO(
                        user.getId(), user.getName(), user.getEmail(),
                        user.getAvatar(), user.getStatus(), user.getRole());
                
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(2 * 60 * 60); // 2 tiếng
                response.addCookie(cookie);


                return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
            } else {
                return ResponseEntity.badRequest().body("Đăng nhập không thành công.!");
            }
        }).orElse(ResponseEntity.badRequest().body("Email không tồn tại"));
    }

}

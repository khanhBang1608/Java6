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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
	private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> login(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   HttpServletResponse response) {
    	
        return userService.findByEmail(email).map(user -> {
        	   if (!user.getStatus()) {
                   return ResponseEntity.badRequest().body("Tài khoản đã bị khóa");
               }
           	


               if (user.getPassword().equals(PasswordUtil.hashPassword(password))) {
               	
               	
               	Authentication authentication = authenticationManager.authenticate(
       					new UsernamePasswordAuthenticationToken(email, password)
       			);
               	

               
               	String role = authentication.getAuthorities().iterator().next().getAuthority();
       			String token = jwtUtil.generateToken(email, role);
       			
       			
                   UserDTO userDTO = new UserDTO(
                           user.getId(), user.getName(), user.getEmail(),
                           user.getAvatar(), user.getStatus(), user.getRole());
                   
                   Cookie cookie = new Cookie("token", token);
                   cookie.setHttpOnly(true);
                   cookie.setPath("/");
                   cookie.setMaxAge(2 * 60 * 60); 
                   response.addCookie(cookie);


                   return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
               } else {
                   return ResponseEntity.badRequest().body("Đăng nhập không thành công.!");
               }
           }).orElse(ResponseEntity.badRequest().body("Đăng nhập không thành công.!"));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // Xóa cookie bằng cách hết hạn
        response.addCookie(cookie);

        return ResponseEntity.ok("Đăng xuất thành công!");
    }


}

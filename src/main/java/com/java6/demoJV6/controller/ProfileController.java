package com.java6.demoJV6.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.java6.demoJV6.dto.UserDTO;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.services.ImageServices;
import com.java6.demoJV6.services.UserService;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageServices imageServices;

    // Cập nhật thông tin profile của người dùng
    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestParam("id") Integer id,
                                           @RequestParam("fullName") String fullName,
                                           @RequestParam("email") String email,
                                           @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {

        Optional<UserEntity> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        }

        UserEntity user = optionalUser.get();
        
        // Kiểm tra và cập nhật thông tin
        user.setName(fullName);
        user.setEmail(email);

        // Xử lý ảnh avatar nếu có
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatar = imageServices.saveImage(avatarFile);
            user.setAvatar(avatar);
        }

        // Lưu lại người dùng với thông tin đã cập nhật
        userService.save(user);
        return ResponseEntity.ok("Cập nhật thông tin thành công");
    }

    // Lấy thông tin profile của người dùng theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable Integer id) {
        Optional<UserEntity> userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = userOpt.get();
        
        // Chuyển đối tượng thành DTO nếu cần (sử dụng UserDTO để trả về thông tin)
        UserDTO userDTO = new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAvatar(),
            user.getStatus(),
            user.getRole()
        );

        return ResponseEntity.ok(userDTO); // Trả về thông tin người dùng
    }
}

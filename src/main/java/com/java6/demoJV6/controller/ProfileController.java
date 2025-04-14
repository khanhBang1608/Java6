package com.java6.demoJV6.controller;

import com.java6.demoJV6.dto.UserDTO;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.services.ImageServices;
import com.java6.demoJV6.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageServices imageServices;

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Integer id) {
        UserEntity user = userService.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại.");
        }
        
        // Chuyển đổi sang DTO để trả về thông tin người dùng
        return ResponseEntity.ok(new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAvatar(),
            user.getStatus(),
            user.getRole()
        ));
    }

    // Cập nhật thông tin hồ sơ người dùng
    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(
        @RequestParam("id") Integer id,
        @RequestParam("fullName") String fullName,
        @RequestParam("email") String email,
        @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        UserEntity user = userService.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại.");
        }

        // Cập nhật các thông tin của người dùng
        user.setName(fullName);
        user.setEmail(email);

        // Nếu có avatar mới, lưu ảnh và cập nhật
        if (avatar != null && !avatar.isEmpty()) {
            String avatarName = imageServices.saveImage(avatar);
            if (avatarName != null) {
                user.setAvatar(avatarName);
            } else {
                return ResponseEntity.badRequest().body("Lỗi khi tải ảnh.");
            }
        }

        userService.save(user); // Lưu lại thông tin đã cập nhật
        return ResponseEntity.ok("Cập nhật hồ sơ thành công.");
    }
}

package com.java6.demoJV6.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.java6.demoJV6.services.EmailService;
import com.java6.demoJV6.services.UserService;
import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.utils.PasswordUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    @PostMapping("/forgot-password")
    public Map<String, Object> forgotPassword(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        UserEntity user = userService.findUserByEmail(email);

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Email không tồn tại.");
            return response;
        }

        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userService.save(user);

        try {
            emailService.sendEmail(email, "Mã OTP khôi phục mật khẩu", "Mã OTP của bạn là: " + otp);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Gửi email thất bại: " + e.getMessage());
            return response;
        }

        response.put("status", "success");
        response.put("message", "OTP đã được gửi đến email.");
        return response;
    }

    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        Map<String, Object> response = new HashMap<>();
        UserEntity user = userService.findUserByEmail(email);

        if (user == null || user.getOtp() == null || user.getOtpExpiry() == null) {
            response.put("status", "error");
            response.put("message", "Không thể xác minh OTP.");
            return response;
        }

        if (LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            response.put("status", "error");
            response.put("message", "Mã OTP đã hết hạn.");
        } else if (!otp.equals(user.getOtp())) {
            response.put("status", "error");
            response.put("message", "Mã OTP không chính xác.");
        } else {
            response.put("status", "success");
            response.put("message", "Xác minh OTP thành công.");
        }

        return response;
    }

    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Map<String, Object> response = new HashMap<>();
        UserEntity user = userService.findUserByEmail(email);

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Không tìm thấy người dùng.");
            return response;
        }

        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        user.setPassword(hashedPassword);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userService.save(user);

        response.put("status", "success");
        response.put("message", "Đặt lại mật khẩu thành công.");
        return response;
    }
}

package com.java6.demoJV6.bean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
    private int id;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6 đến 100 ký tự")
    private String password;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    private String fullName;

    private Integer gender; // 0: Nữ, 1: Nam (đã có CHECK constraint)

    private Date dateCreated; // default GETDATE()

    private String avatar;

    private Integer status; // trạng thái tài khoản

    private Integer role; // quyền (admin, user...)

    private String resetToken;

    private Date otpExpiry;

    // Các trường dành riêng cho đổi mật khẩu
    private String currentPassword;   // mật khẩu hiện tại
    private String newPassword;       // mật khẩu mới
    private String confirmPassword;   // xác nhận mật khẩu mới
}
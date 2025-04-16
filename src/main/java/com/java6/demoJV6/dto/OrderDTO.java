package com.java6.demoJV6.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDTO {
	private Integer orderId;
	private String fullName;
	private String customerName;
	private String phone;
	private Integer status;
	private LocalDateTime orderDate;
	private double totalAmount;
	private String address;
	private Integer userId;
}


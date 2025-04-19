package com.java6.demoJV6.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    private Integer userId;
    private String address;
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private Integer productSizeId;
        private Integer quantity;
    }
}

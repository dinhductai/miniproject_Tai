package com.microsv.order_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class OrderResponse {
    private Long orderId;
    private String productName;
    private UserResponse user; // Lồng thông tin user vào đây


    public OrderResponse(Long orderId, String productName, UserResponse user) {
        this.orderId = orderId;
        this.productName = productName;
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
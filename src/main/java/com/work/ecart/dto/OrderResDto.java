package com.work.ecart.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResDto {

    private String payment;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private int customerId;
    private int productId;
    private int cartId;
}

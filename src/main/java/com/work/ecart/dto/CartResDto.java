package com.work.ecart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartResDto {
    private String customerName;
    private String productName;
    private String customerAddress;
    private String customerPhone;
}
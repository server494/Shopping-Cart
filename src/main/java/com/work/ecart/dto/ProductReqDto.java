package com.work.ecart.dto;


import lombok.Data;

@Data
public class ProductReqDto {

    private String name;
    private String productCode;
    private Integer rate;
    private String brand;
    private int category_id;

}

package com.work.ecart.dto;

import lombok.Data;

@Data
public class CustomerReqDto {
    private String name;
    private String phoneNumber;
    private String location;
    private String address;
    private String aadharNo;
    private String password;
}

package com.work.ecart.entity;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

@Entity
@Data

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phoneNumber;
    private String location;
    private String address;
    private String password;

    @OneToMany(mappedBy = "customer")
    List<Cart> cart;

    @OneToMany(mappedBy = "customer")
    List<Orders> orders;

}

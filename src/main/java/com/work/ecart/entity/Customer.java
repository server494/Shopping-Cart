package com.work.ecart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String aadharNo;
    private String password;

    @OneToMany(mappedBy = "customer")
    List<Orders> orders;

}

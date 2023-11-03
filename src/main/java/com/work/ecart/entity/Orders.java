package com.work.ecart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data

public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String payment;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private boolean delivered;


    @ManyToOne
    @JoinColumn(name = "customerId",referencedColumnName ="id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;

}

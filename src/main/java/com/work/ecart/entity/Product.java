package com.work.ecart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Entity
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rate;
    private String brand;
    private String name;
    private String productCode;
    @Lob
    private byte[] productImage;
    private String productImgName;

    @OneToMany(mappedBy = "product")
    List<Orders> orders;

    @OneToMany(mappedBy = "product")
    List<Cart> cart;

    @OneToOne
    @JoinColumn(name = "categoryId",referencedColumnName = "id")
    private Category category;

}

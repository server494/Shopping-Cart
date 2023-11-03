package com.work.ecart.repository;

import com.work.ecart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    //JPQL
    @Query("SELECT p FROM Product p where p.rate >=:r")
    public List<Product> filterProductByRateAbove(@Param("r")int rate);

    @Query("SELECT p FROM Product p where p.rate <=:r")
    public List<Product> filterProductByRateBelow(@Param("r")int rate);

}

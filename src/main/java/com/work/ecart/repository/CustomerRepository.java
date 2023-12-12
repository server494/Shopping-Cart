package com.work.ecart.repository;

import com.work.ecart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c where c.location =:l")
    List<Customer> getCustomerByLocation(@Param("l") String location);

    @Query("SELECT c FROM Customer c where c.name =:n")
    List<Customer> getCustomerByName(@Param("n") String name);

}

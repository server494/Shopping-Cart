package com.work.ecart.service;

import com.work.ecart.dto.CustomerReqDto;
import com.work.ecart.dto.CustomerResDto;
import com.work.ecart.dto.GenericResponse;
import com.work.ecart.exception.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {

    GenericResponse saveCustomer(CustomerReqDto customerReqDto);

    GenericResponse getAllCustomers(Integer pageNo, Integer pageSize);

    GenericResponse updateCustomer(Integer id, CustomerReqDto customerReqDto) throws ResourceNotFoundException;

    GenericResponse getCustomerByLocation(String location);

    GenericResponse deleteCustomer(Integer id) throws ResourceNotFoundException;
}
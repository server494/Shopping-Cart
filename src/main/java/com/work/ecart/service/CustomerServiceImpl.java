package com.work.ecart.service;

import com.work.ecart.dto.CustomerReqDto;
import com.work.ecart.dto.CustomerResDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.entity.Customer;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GenericResponse saveCustomer(CustomerReqDto customerReqDto) {

        GenericResponse genericResponse = new GenericResponse<>();

        Customer customer = new Customer();
        customer.setName(customerReqDto.getName());
        customer.setAddress(customerReqDto.getAddress());
        customer.setLocation(customerReqDto.getLocation());
        customer.setPassword(customerReqDto.getPassword());
        customer.setPhoneNumber(customerReqDto.getPhoneNumber());
        customerRepository.save(customer);

        CustomerResDto customerResDto = new CustomerResDto();
        customerResDto.setName(customer.getName());
        customerResDto.setAddress(customer.getAddress());
        customerResDto.setPhoneNumber(customer.getPhoneNumber());
        customerResDto.setLocation(customer.getLocation());

        genericResponse.setData(customerResDto);
        genericResponse.setMessage("Customer saved successfully");
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse getAllCustomers(Integer pageNo, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Customer> page = customerRepository.findAll(pageRequest);
        List<Customer> customers = page.getContent();

        List<CustomerResDto> customerResDtoList = customers
                .stream().map(customer -> modelMapper.map(customer, CustomerResDto.class))
                .collect(Collectors.toList());

        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setSuccess(true);
        genericResponse.setData(customerResDtoList);
        genericResponse.setMessage("Customers fetched successfully ");
        return genericResponse;

    }

    @Override
    public GenericResponse updateCustomer(Integer id, CustomerReqDto customerReqDto) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        customer.setPhoneNumber(customerReqDto.getPhoneNumber());
        customer.setAddress(customerReqDto.getAddress());
        customer.setLocation(customerReqDto.getLocation());
        customer.setPassword(customerReqDto.getPassword());
        customer.setName(customerReqDto.getName());
        customerRepository.save(customer);

        CustomerResDto customerResDto = new CustomerResDto();
        customerResDto.setPhoneNumber(customer.getPhoneNumber());
        customerResDto.setAddress(customer.getAddress());
        customerResDto.setName(customer.getName());
        customerResDto.setLocation(customer.getLocation());

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("Customer updated successfully");
        genericResponse.setData(customerResDto);
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse getCustomerByLocation(String location) {

        List<Customer> customers = customerRepository.getCustomerByLocation(location);
        GenericResponse genericResponse = new GenericResponse<>();

        List<CustomerResDto> customerResDtoList = customers
                .stream().map(customer -> modelMapper.map(customer, CustomerResDto.class))
                .collect(Collectors.toList());

        genericResponse.setData(customerResDtoList);
        genericResponse.setMessage("List fetch successfully");
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse getCustomersByName(String name) {

        GenericResponse genericResponse = new GenericResponse();
        List<Customer> customers = customerRepository.getCustomerByName(name);
        List<CustomerResDto> customerResDtoList = customers
                .stream().map(customer -> modelMapper.map(customer, CustomerResDto.class))
                .collect(Collectors.toList());

        genericResponse.setData(customerResDtoList);
        genericResponse.setMessage("List fetch successfully");
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse deleteCustomer(Integer id) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setSuccess(true);
        genericResponse.setData("[]");
        genericResponse.setMessage("Customer deleted successfully");
        customerRepository.delete(customer);
        return genericResponse;
    }

}

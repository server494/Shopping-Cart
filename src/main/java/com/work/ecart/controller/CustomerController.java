package com.work.ecart.controller;

import com.work.ecart.dto.CustomerReqDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.service.CustomerService;
import com.work.ecart.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCustomer")
    public ResponseEntity saveCustomer(@RequestBody CustomerReqDto customerReqDto) {

        GenericResponse genericResponse = null;
        try {
            genericResponse = customerService.saveCustomer(customerReqDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to save customer", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity retrieveAllCustomers(@RequestParam(name = "PAGE NO", defaultValue = "0", required = false) Integer pageNo,
                                               @RequestParam(name = "PAGE SIZE", defaultValue = "1", required = false) Integer pageSize) {

        GenericResponse genericResponse = null;
        try {
            genericResponse = customerService.getAllCustomers(pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to retrieve All customers", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @GetMapping("/getCustomersByLocation/{location}")
    public ResponseEntity getCustomersByLocation(@PathVariable String location) {
        GenericResponse genericResponse = null;
        try {
            genericResponse = customerService.getCustomerByLocation(location);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to retrieve customers by location", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id, @RequestBody CustomerReqDto customerReqDto) {

        GenericResponse genericResponse = null;
        try {
            genericResponse = customerService.updateCustomer(id, customerReqDto);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to update customer", 400), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new GenericResponse(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity deleteMapping(@PathVariable Integer id) {
        GenericResponse genericResponse = null;
        try {
            genericResponse = customerService.deleteCustomer(id);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to delete customer ", 400), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()), HttpStatus.OK);
    }

}

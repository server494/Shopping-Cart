package com.work.ecart.controller;

import com.work.ecart.dto.GenericResponse;
import com.work.ecart.dto.OrderReqDto;
import com.work.ecart.dto.OrderResDto;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.service.OrderService;
import com.work.ecart.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/saveOrder")
    public ResponseEntity saveOrder(@RequestBody OrderReqDto orderReqDto) throws ResourceNotFoundException {

        GenericResponse genericResponse=null;
        try {
            logger.info("Started saving order.....");
            genericResponse=orderService.saveOrder(orderReqDto);
        }catch (ResourceNotFoundException e){
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to save order",400), HttpStatus.BAD_REQUEST);
        }
        logger.info(genericResponse.getMessage());
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(),genericResponse.getMessage(),genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity getAllOrders(@RequestParam(name = "PAGE NO",defaultValue = "0",required = false)Integer pageNo,
                                       @RequestParam(name = "PAGE SIZE",defaultValue = "1",required = false)Integer pageSize){

        GenericResponse genericResponse=null;
        try {
            logger.info("Started retrieving all orders.....");
            genericResponse=orderService.getAllOrders(pageNo,pageSize);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to retrieve orders",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(),genericResponse.getMessage(),genericResponse.isSuccess()),
                HttpStatus.OK);    }
}

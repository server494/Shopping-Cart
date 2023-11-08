package com.work.ecart.controller;

import com.work.ecart.dto.CartReqDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.service.CartService;
import com.work.ecart.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/saveCart")
    public ResponseEntity saveCart(@RequestBody CartReqDto cartreqDto) {

        GenericResponse genericResponse = null;
        try {
            genericResponse = cartService.saveCart(cartreqDto);
        } catch (ResourceNotFoundException e) {
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to save cart", 400), HttpStatus.BAD_REQUEST);
        }
        logger.info(genericResponse.getMessage());
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

}

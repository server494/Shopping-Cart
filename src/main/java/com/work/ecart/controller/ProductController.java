package com.work.ecart.controller;

import com.work.ecart.dto.GenericResponse;
import com.work.ecart.dto.ProductReqDto;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.service.ProductService;
import com.work.ecart.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private static final Logger logger=LoggerFactory.getLogger(ProductController.class);
    @PostMapping("/saveProduct")
    public ResponseEntity saveProduct(@ModelAttribute ProductReqDto productReqDto,
                                                       @RequestParam MultipartFile file) {
        GenericResponse genericResponse = null;
        try {
            genericResponse = productService.saveProduct(productReqDto,file);
        }catch (ResourceNotFoundException|IOException e){
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to save product",400),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new GenericResponse(genericResponse.getData(),genericResponse.getMessage(), genericResponse.isSuccess()),HttpStatus.OK);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id){

        GenericResponse genericResponse=null;
        try {
            genericResponse=productService.findProductById(id);
        } catch (ResourceNotFoundException e) {
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to find product",400),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new GenericResponse(genericResponse.getData(),genericResponse.getMessage(), genericResponse.isSuccess()),HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts(@RequestParam(value = "pageNo",defaultValue = "0",required = false)Integer pageNumber,
                                                          @RequestParam(value = "pageSize",defaultValue = "1",required = false)Integer pageSize){
        GenericResponse genericResponse=null;
        try {
            genericResponse=productService.getAllProducts(pageNumber,pageSize);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to retrieve all products",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericResponse(genericResponse.getData(),genericResponse.getMessage(), genericResponse.isSuccess()),HttpStatus.OK);
    }
    @GetMapping("/getProductsAboveRange/{rate}")
    public ResponseEntity getProductsAboveRange(@PathVariable Integer rate){
        GenericResponse genericResponse = null;
        try {
            genericResponse=productService.filterProductAbove(rate);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to filter products",500),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericResponse<>(genericResponse.getData(),genericResponse.getMessage(),
                genericResponse.isSuccess()),HttpStatus.OK);
    }


    @PutMapping("/updateBooking/{id}")
    public ResponseEntity updateProductById(@PathVariable Integer id,
                                                             @ModelAttribute ProductReqDto productReqDto,
                                                             @RequestParam MultipartFile productImage){

        GenericResponse genericResponse=null;
        try {
            genericResponse=productService.updateProduct(productReqDto,productImage,id);
        } catch (IOException | ResourceNotFoundException e) {
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to update product ",400),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new GenericResponse(genericResponse.getData(),genericResponse.getMessage(), genericResponse.isSuccess()),HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProductById(@PathVariable Integer id){

        GenericResponse genericResponse = null;

        try {
            genericResponse=productService.deleteProduct(id);
        } catch (ResourceNotFoundException e) {
            logger.error(e.toString());
            return new ResponseEntity<>(new ErrorResponse("Unable to delete product",400),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new GenericResponse<>(genericResponse.getData(),genericResponse.getMessage(),
                genericResponse.isSuccess()),HttpStatus.OK);
    }
}

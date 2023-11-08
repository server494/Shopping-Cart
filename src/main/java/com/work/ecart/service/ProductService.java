package com.work.ecart.service;

import com.work.ecart.util.GenericResponse;
import com.work.ecart.dto.ProductReqDto;
import com.work.ecart.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {

    GenericResponse saveProduct(ProductReqDto productReqDto, MultipartFile file) throws ResourceNotFoundException, IOException;

    GenericResponse getAllProducts(Integer pageNo, Integer pageSize);

    GenericResponse updateProduct(ProductReqDto productReqDto, MultipartFile file, Integer id) throws ResourceNotFoundException, IOException;

    GenericResponse deleteProduct(Integer id) throws ResourceNotFoundException;

    GenericResponse filterProductAbove(Integer rate);

    GenericResponse findProductById(Integer id) throws ResourceNotFoundException;
}

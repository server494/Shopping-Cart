package com.work.ecart.service;

import com.work.ecart.dto.GenericResponse;
import com.work.ecart.dto.ProductReqDto;
import com.work.ecart.dto.ProductResDto;
import com.work.ecart.entity.Category;
import com.work.ecart.entity.Product;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.repository.CategoryRepository;
import com.work.ecart.repository.ProductRepository;
import com.work.ecart.util.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GenericResponse saveProduct(ProductReqDto productReqDto, MultipartFile file) throws ResourceNotFoundException, IOException {

        GenericResponse genericResponse=new GenericResponse<>();

        Category category = categoryRepository.findById(productReqDto.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productReqDto.getCategory_id()));


        Product product = new Product();
        product.setName(productReqDto.getName());
        product.setProductImage(file.getBytes());
        product.setProductCode(productReqDto.getProductCode());
        product.setBrand(productReqDto.getBrand());
        product.setRate(productReqDto.getRate());
        product.setProductImgName(file.getOriginalFilename());
        product.setCategory(category);
        productRepository.save(product);

        ProductResDto productResDto = new ProductResDto();
        productResDto.setName(product.getName());
        productResDto.setCategoryType(product.getCategory().getType());
        productResDto.setRate(product.getRate());
        productResDto.setBrand(product.getBrand());

        genericResponse.setSuccess(true);
        genericResponse.setMessage("Product Saved");
        genericResponse.setData(productResDto);
        return genericResponse;
    }

    @Override
    public GenericResponse getAllProducts(Integer pageNo, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        Page<Product> page = productRepository.findAll(pageRequest);
        List<Product> productList = page.getContent();

        List<ProductResDto> productResDtoList = productList.stream()
                .map(product -> modelMapper.map(product,ProductResDto.class))
                .collect(Collectors.toList());

        GenericResponse genericResponse=new GenericResponse<>();
        genericResponse.setData(productResDtoList);
        genericResponse.setMessage("List of products retrieved successfully ");
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse updateProduct(ProductReqDto productReqDto,MultipartFile file,Integer id) throws ResourceNotFoundException, IOException {

        Product product=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("product","id",id));
        Category category=categoryRepository.findById(productReqDto.getCategory_id())
                .orElseThrow(()->new ResourceNotFoundException("category","id",id));


        product.setRate(productReqDto.getRate());
        product.setProductImage(file.getBytes());
        product.setCategory(category);
        product.setProductCode(productReqDto.getProductCode());
        product.setBrand(productReqDto.getBrand());
        product.setRate(productReqDto.getRate());
        product.setName(productReqDto.getName());
        product.setProductImgName(file.getOriginalFilename());
        productRepository.save(product);

        ProductResDto productResDto =new ProductResDto();
        productResDto.setName(product.getName());
        productResDto.setRate(product.getRate());
        productResDto.setBrand(product.getBrand());
        productResDto.setCategoryType(product.getCategory().getType());

        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setSuccess(true);
        genericResponse.setData(productResDto);
        genericResponse.setMessage("Product successfully updated");
        return genericResponse;
    }

    @Override
    public GenericResponse deleteProduct(Integer id) throws ResourceNotFoundException {

        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product","id",id));
        ProductResDto productResDto = new ProductResDto();
        productResDto.setCategoryType(product.getCategory().getType());
        productResDto.setName(product.getName());
        productResDto.setRate(product.getRate());
        productResDto.setBrand(product.getBrand());

        productRepository.delete(product);
        GenericResponse genericResponse =new GenericResponse<>();
        genericResponse.setSuccess(true);
        genericResponse.setData(productResDto);
        genericResponse.setMessage("Product deleted successfully");
        return genericResponse;
    }

    @Override
    public GenericResponse filterProductAbove(Integer rate) {

        List<Product> productList = productRepository.filterProductByRateAbove(rate);
        List<ProductResDto> productResDtoList=productList
                .stream().map(product -> modelMapper.map(product,ProductResDto.class))
                .collect(Collectors.toList());
        GenericResponse genericResponse=new GenericResponse<>();
        genericResponse.setMessage("Filter successful");
        genericResponse.setSuccess(true);
        genericResponse.setData(productResDtoList);

        return genericResponse;
    }
    @Override
    public GenericResponse findProductById(Integer id) throws ResourceNotFoundException {

        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product","id",id));
        ProductResDto productResDto=new ProductResDto();
        productResDto.setBrand(product.getBrand());
        productResDto.setRate(product.getRate());
        productResDto.setCategoryType(product.getCategory().getType());
        productResDto.setName(product.getName());
        GenericResponse genericResponse=new GenericResponse<>();
        genericResponse.setMessage("Product found successfully");
        genericResponse.setData(productResDto);
        genericResponse.setSuccess(true);
        return genericResponse;
    }
}

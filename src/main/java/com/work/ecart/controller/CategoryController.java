package com.work.ecart.controller;

import com.work.ecart.dto.CategoryDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.service.CategoryService;
import com.work.ecart.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {


    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/saveCategory")
    public ResponseEntity saveCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Category saving started.....");
        GenericResponse genericResponse = null;
        try {
            genericResponse = categoryService.saveCategory(categoryDto);
            logger.info("Category saving ended.....");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to save category", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }


    @GetMapping("/getAllCategory")
    public ResponseEntity viewAllCategory(@RequestParam(value = "PAGE NO", defaultValue = "1", required = false) Integer pageNo,
                                          @RequestParam(value = "PAGE SIZE", defaultValue = "1", required = false) Integer pageSize) {

        logger.info("Retrieving all category started.....");
        GenericResponse genericResponse = null;
        try {
            genericResponse = categoryService.getAllCategories(pageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to retrieve all category", 400), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @GetMapping("/viewById/{id}")
    public ResponseEntity viewCategory(@PathVariable Integer id) throws ResourceNotFoundException {

        logger.info("Retrieving category by id started......");
        GenericResponse genericResponse = null;
        try {
            genericResponse = categoryService.getCategoryById(id);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Category not found", 400), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) throws ResourceNotFoundException {

        logger.info("Updating category by id........");
        GenericResponse genericResponse = null;
        try {
            genericResponse = categoryService.updateCategory(id, categoryDto);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity(new ErrorResponse("Category not found", 400), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id) throws ResourceNotFoundException {
        logger.info("Deleting category started");
        GenericResponse genericResponse = null;
        try {
            genericResponse = categoryService.deleteCategory(id);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ErrorResponse("Unable to delete category", 400),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new GenericResponse<>(genericResponse.getData(), genericResponse.getMessage(), genericResponse.isSuccess()),
                HttpStatus.OK);
    }

}






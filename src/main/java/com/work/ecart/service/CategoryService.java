package com.work.ecart.service;

import com.work.ecart.dto.CategoryDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.exception.ResourceNotFoundException;


public interface CategoryService {

    GenericResponse saveCategory(CategoryDto categoryDto);

    GenericResponse getAllCategories(Integer pageNo, Integer pageSize);

    GenericResponse getCategoryById(Integer id) throws ResourceNotFoundException;

    GenericResponse updateCategory(Integer id, CategoryDto categoryDto) throws ResourceNotFoundException;

    GenericResponse deleteCategory(Integer id) throws ResourceNotFoundException;
}

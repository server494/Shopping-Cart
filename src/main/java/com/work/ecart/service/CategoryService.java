package com.work.ecart.service;

import com.work.ecart.dto.CategoryDto;
import com.work.ecart.dto.GenericResponse;
import com.work.ecart.entity.Category;
import com.work.ecart.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    GenericResponse saveCategory(CategoryDto categoryDto);

    GenericResponse getAllCategories(Integer pageNo,Integer pageSize);

    GenericResponse getCategoryById(Integer id) throws ResourceNotFoundException;

    GenericResponse updateCategory(Integer id,CategoryDto categoryDto) throws ResourceNotFoundException;

    GenericResponse deleteCategory(Integer id) throws ResourceNotFoundException;
}

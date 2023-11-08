package com.work.ecart.service;

import com.work.ecart.dto.CategoryDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.entity.Category;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public GenericResponse saveCategory(CategoryDto categoryDto) {

        Category category = new Category();
        category.setType(categoryDto.getType());
        categoryRepository.save(category);
        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setMessage("Category added successfully");
        genericResponse.setData(category.getType());
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse getAllCategories(Integer pageNo, Integer pageSize) {

        GenericResponse genericResponse = new GenericResponse<>();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Category> page = categoryRepository.findAll(pageRequest);
        List<Category> pagesList = page.getContent();

        List<CategoryDto> categoryDtoList = pagesList.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        genericResponse.setData(categoryDtoList);
        genericResponse.setMessage("Category list retrieved successfully");
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse getCategoryById(Integer id) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        GenericResponse genericResponse = new GenericResponse<>();

        genericResponse.setData(category.getType());
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Category found successfully");
        return genericResponse;
    }

    @Override
    public GenericResponse updateCategory(Integer id, CategoryDto categoryDto) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        GenericResponse genericResponse = new GenericResponse();

        category.setType(categoryDto.getType());
        categoryRepository.save(category);
        genericResponse.setData(category.getType());
        genericResponse.setMessage("Category updated successfully");
        genericResponse.setSuccess(true);

        return genericResponse;
    }

    @Override
    public GenericResponse deleteCategory(Integer id) throws ResourceNotFoundException {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setSuccess(true);
        genericResponse.setData(category.getType());
        genericResponse.setMessage("Category deleted successfully");
        categoryRepository.delete(category);

        return genericResponse;
    }
}

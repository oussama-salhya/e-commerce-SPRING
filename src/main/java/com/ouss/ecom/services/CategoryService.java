package com.ouss.ecom.services;

import com.ouss.ecom.dao.CategoryRepo;
import com.ouss.ecom.entities.Category;
import com.ouss.ecom.errors.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    public Category createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) != null) {
            throw new CustomException.BadRequestException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getSingleCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new CustomException.NotFoundException("Category not found"));
    }

    public Category updateCategory(Long id, Category category) {
        Category existingCategory = getSingleCategory(id);
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
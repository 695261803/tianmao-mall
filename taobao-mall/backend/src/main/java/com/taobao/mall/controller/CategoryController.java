package com.taobao.mall.controller;

import com.taobao.mall.dto.ApiResponse;
import com.taobao.mall.entity.Category;
import com.taobao.mall.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories);
    }
    
    @GetMapping("/root")
    public ApiResponse<List<Category>> getRootCategories() {
        List<Category> categories = categoryService.getRootCategories();
        return ApiResponse.success(categories);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Category> getCategory(@PathVariable Long id) {
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isPresent()) {
            return ApiResponse.success(categoryOpt.get());
        } else {
            return ApiResponse.error("分类不存在");
        }
    }
    
    @GetMapping("/{id}/children")
    public ApiResponse<List<Category>> getSubCategories(@PathVariable Long id) {
        Optional<Category> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isEmpty()) {
            return ApiResponse.error("分类不存在");
        }
        
        List<Category> subCategories = categoryService.getSubCategories(categoryOpt.get());
        return ApiResponse.success(subCategories);
    }
}
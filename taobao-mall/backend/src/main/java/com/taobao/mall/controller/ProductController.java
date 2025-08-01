package com.taobao.mall.controller;

import com.taobao.mall.dto.ApiResponse;
import com.taobao.mall.entity.Product;
import com.taobao.mall.entity.Category;
import com.taobao.mall.service.ProductService;
import com.taobao.mall.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    
    @GetMapping
    public ApiResponse<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.getAllProducts(pageable);
        
        return ApiResponse.success(products);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Product> getProduct(@PathVariable Long id) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isPresent()) {
            // 增加浏览量
            productService.incrementViews(id);
            return ApiResponse.success(productOpt.get());
        } else {
            return ApiResponse.error("商品不存在");
        }
    }
    
    @GetMapping("/search")
    public ApiResponse<Page<Product>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProducts(keyword, pageable);
        
        return ApiResponse.success(products);
    }
    
    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<Product>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        
        Optional<Category> categoryOpt = categoryService.getCategoryById(categoryId);
        if (categoryOpt.isEmpty()) {
            return ApiResponse.error("分类不存在");
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getProductsByCategory(categoryOpt.get(), pageable);
        
        return ApiResponse.success(products);
    }
    
    @GetMapping("/hot")
    public ApiResponse<Page<Product>> getHotProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getHotProducts(pageable);
        
        return ApiResponse.success(products);
    }
    
    @GetMapping("/latest")
    public ApiResponse<Page<Product>> getLatestProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getLatestProducts(pageable);
        
        return ApiResponse.success(products);
    }
    
    @GetMapping("/price-range")
    public ApiResponse<Page<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
        
        return ApiResponse.success(products);
    }
}
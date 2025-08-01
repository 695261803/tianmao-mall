package com.taobao.mall.controller;

import com.taobao.mall.dto.ApiResponse;
import com.taobao.mall.entity.Cart;
import com.taobao.mall.entity.User;
import com.taobao.mall.service.CartService;
import com.taobao.mall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {
    
    private final CartService cartService;
    private final UserService userService;
    
    @GetMapping
    public ApiResponse<Cart> getCart(@RequestHeader("Authorization") String token) {
        // 这里应该从JWT中获取用户信息，简化处理
        // 实际项目中需要实现JWT过滤器
        return ApiResponse.error("需要登录");
    }
    
    @PostMapping("/add")
    public ApiResponse<Cart> addToCart(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> request) {
        
        try {
            Long productId = Long.valueOf(request.get("productId").toString());
            Integer quantity = Integer.valueOf(request.get("quantity").toString());
            
            // 这里需要从JWT中获取用户信息
            // 暂时使用固定用户ID进行演示
            Optional<User> userOpt = userService.findById(1L);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("用户不存在");
            }
            
            Cart cart = cartService.addItemToCart(userOpt.get(), productId, quantity);
            return ApiResponse.success("添加成功", cart);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public ApiResponse<Cart> updateCartItem(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> request) {
        
        try {
            Long productId = Long.valueOf(request.get("productId").toString());
            Integer quantity = Integer.valueOf(request.get("quantity").toString());
            
            Optional<User> userOpt = userService.findById(1L);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("用户不存在");
            }
            
            Cart cart = cartService.updateCartItemQuantity(userOpt.get(), productId, quantity);
            return ApiResponse.success("更新成功", cart);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/remove/{productId}")
    public ApiResponse<Cart> removeFromCart(
            @RequestHeader("Authorization") String token,
            @PathVariable Long productId) {
        
        try {
            Optional<User> userOpt = userService.findById(1L);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("用户不存在");
            }
            
            Cart cart = cartService.removeItemFromCart(userOpt.get(), productId);
            return ApiResponse.success("删除成功", cart);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart(@RequestHeader("Authorization") String token) {
        try {
            Optional<User> userOpt = userService.findById(1L);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("用户不存在");
            }
            
            cartService.clearCart(userOpt.get());
            return ApiResponse.success("清空成功");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
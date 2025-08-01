package com.taobao.mall.service;

import com.taobao.mall.entity.Cart;
import com.taobao.mall.entity.CartItem;
import com.taobao.mall.entity.Product;
import com.taobao.mall.entity.User;
import com.taobao.mall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    private final ProductService productService;
    
    public Cart getCartByUser(User user) {
        Optional<Cart> cartOpt = cartRepository.findByUser(user);
        if (cartOpt.isPresent()) {
            return cartOpt.get();
        } else {
            // 如果用户没有购物车，创建一个新的
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        }
    }
    
    public Cart addItemToCart(User user, Long productId, Integer quantity) {
        Cart cart = getCartByUser(user);
        Optional<Product> productOpt = productService.getProductById(productId);
        
        if (productOpt.isEmpty()) {
            throw new RuntimeException("商品不存在");
        }
        
        Product product = productOpt.get();
        
        // 检查购物车中是否已有该商品
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // 如果已有该商品，增加数量
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // 如果没有该商品，添加新的购物车项
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        }
        
        return cartRepository.save(cart);
    }
    
    public Cart updateCartItemQuantity(User user, Long productId, Integer quantity) {
        Cart cart = getCartByUser(user);
        
        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        
        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (quantity <= 0) {
                cart.getCartItems().remove(cartItem);
            } else {
                cartItem.setQuantity(quantity);
            }
            return cartRepository.save(cart);
        } else {
            throw new RuntimeException("购物车中没有该商品");
        }
    }
    
    public Cart removeItemFromCart(User user, Long productId) {
        Cart cart = getCartByUser(user);
        
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        
        return cartRepository.save(cart);
    }
    
    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
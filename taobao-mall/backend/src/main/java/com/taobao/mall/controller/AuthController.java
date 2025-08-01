package com.taobao.mall.controller;

import com.taobao.mall.config.JwtUtil;
import com.taobao.mall.dto.ApiResponse;
import com.taobao.mall.dto.UserLoginRequest;
import com.taobao.mall.dto.UserRegisterRequest;
import com.taobao.mall.entity.User;
import com.taobao.mall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ApiResponse<Map<String, Object>> register(@Validated @RequestBody UserRegisterRequest request) {
        try {
            User user = userService.register(request);
            String token = jwtUtil.generateToken(user.getUsername());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);
            
            return ApiResponse.success("注册成功", response);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Validated @RequestBody UserLoginRequest request) {
        Optional<User> userOpt = userService.login(request);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getUsername());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);
            
            return ApiResponse.success("登录成功", response);
        } else {
            return ApiResponse.error("用户名或密码错误");
        }
    }
}
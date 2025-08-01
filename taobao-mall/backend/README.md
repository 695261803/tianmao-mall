# 淘宝商城后端

基于Spring Boot的电商平台后端API服务。

## 技术栈

- **Java 17** - 编程语言
- **Spring Boot 3.2.0** - 后端框架
- **Spring Data JPA** - 数据访问层
- **Spring Security** - 安全框架
- **MySQL 8.0** - 数据库
- **JWT** - 身份认证
- **Lombok** - 代码简化
- **Maven** - 项目管理

## 项目结构

```
src/
├── main/
│   ├── java/com/taobao/mall/
│   │   ├── controller/        # REST控制器
│   │   │   ├── AuthController.java       # 认证相关
│   │   │   ├── ProductController.java    # 商品相关
│   │   │   ├── CategoryController.java   # 分类相关
│   │   │   └── CartController.java       # 购物车相关
│   │   ├── service/           # 业务逻辑层
│   │   │   ├── UserService.java
│   │   │   ├── ProductService.java
│   │   │   ├── CategoryService.java
│   │   │   └── CartService.java
│   │   ├── repository/        # 数据访问层
│   │   │   ├── UserRepository.java
│   │   │   ├── ProductRepository.java
│   │   │   ├── CategoryRepository.java
│   │   │   ├── OrderRepository.java
│   │   │   ├── CartRepository.java
│   │   │   └── AddressRepository.java
│   │   ├── entity/            # 实体类
│   │   │   ├── User.java
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── Order.java
│   │   │   ├── OrderItem.java
│   │   │   ├── Cart.java
│   │   │   ├── CartItem.java
│   │   │   └── Address.java
│   │   ├── dto/               # 数据传输对象
│   │   │   ├── ApiResponse.java
│   │   │   ├── UserLoginRequest.java
│   │   │   └── UserRegisterRequest.java
│   │   ├── config/            # 配置类
│   │   │   ├── SecurityConfig.java
│   │   │   └── JwtUtil.java
│   │   └── MallApplication.java  # 启动类
│   └── resources/
│       └── application.yml       # 配置文件
└── test/                         # 测试代码
```

## 快速开始

### 环境要求
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### 安装启动

1. 克隆项目：
```bash
git clone <repository-url>
cd taobao-mall/backend
```

2. 配置数据库：
```sql
-- 创建数据库
CREATE DATABASE taobao_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 修改配置文件 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/taobao_mall?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
    username: your_username
    password: your_password
```

4. 安装依赖并启动：
```bash
mvn clean install
mvn spring-boot:run
```

服务将在 `http://localhost:8080` 启动

### 数据库初始化

应用启动时会自动创建表结构（通过JPA的`ddl-auto: update`配置）。

可以手动插入一些测试数据：

```sql
-- 插入分类数据
INSERT INTO categories (name, description, sort_order, created_at, updated_at) VALUES
('电子产品', '各类电子产品', 1, NOW(), NOW()),
('服装', '男女服装', 2, NOW(), NOW()),
('家居用品', '家居生活用品', 3, NOW(), NOW());

-- 插入商品数据
INSERT INTO products (name, description, price, original_price, stock, main_image, category_id, status, created_at, updated_at) VALUES
('iPhone 15', '最新款iPhone手机', 6999.00, 7999.00, 100, 'https://example.com/iphone15.jpg', 1, 'ACTIVE', NOW(), NOW()),
('MacBook Pro', '苹果笔记本电脑', 12999.00, 13999.00, 50, 'https://example.com/macbook.jpg', 1, 'ACTIVE', NOW(), NOW());
```

## API接口

### 基础响应格式
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 认证接口

#### 用户注册
```
POST /api/auth/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "123456",
  "nickname": "测试用户",
  "phone": "13800138000"
}
```

#### 用户登录
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "123456"
}
```

### 商品接口

#### 获取商品列表
```
GET /api/products?page=0&size=12&sortBy=id&sortDir=desc
```

#### 获取商品详情
```
GET /api/products/{id}
```

#### 搜索商品
```
GET /api/products/search?keyword=iPhone&page=0&size=12
```

#### 获取热门商品
```
GET /api/products/hot?page=0&size=8
```

#### 按分类获取商品
```
GET /api/products/category/{categoryId}?page=0&size=12
```

### 分类接口

#### 获取所有分类
```
GET /api/categories
```

#### 获取根分类
```
GET /api/categories/root
```

### 购物车接口

#### 添加商品到购物车
```
POST /api/cart/add
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

#### 更新购物车商品数量
```
PUT /api/cart/update
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 3
}
```

## 配置说明

### 应用配置 (application.yml)

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  application:
    name: taobao-mall-backend
  
  # 数据库配置
  datasource:
    url: jdbc:mysql://localhost:3306/taobao_mall
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  # JPA配置
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect

# JWT配置
jwt:
  secret: your-secret-key
  expiration: 86400000  # 24小时

# 文件上传配置
file:
  upload:
    path: /uploads/
    max-size: 10MB
```

### 安全配置

- 使用Spring Security + JWT进行身份认证
- `/api/auth/**` 路径允许匿名访问
- `/api/products/**` 和 `/api/categories/**` 允许匿名访问
- 其他路径需要身份认证

## 数据库设计

### 用户表 (users)
- id - 主键
- username - 用户名（唯一）
- email - 邮箱（唯一）
- password - 密码（加密）
- nickname - 昵称
- phone - 手机号
- avatar - 头像
- gender - 性别
- status - 状态
- created_at - 创建时间
- updated_at - 更新时间

### 商品表 (products)
- id - 主键
- name - 商品名称
- description - 商品描述
- price - 价格
- original_price - 原价
- stock - 库存
- main_image - 主图
- category_id - 分类ID
- status - 状态
- sales - 销量
- views - 浏览量
- created_at - 创建时间
- updated_at - 更新时间

### 其他核心表
- categories - 分类表
- orders - 订单表
- order_items - 订单项表
- carts - 购物车表
- cart_items - 购物车项表
- addresses - 地址表

## 开发指南

### 代码规范
- 使用Java标准命名规范
- 使用Lombok减少样板代码
- 所有公共方法需要添加注释
- 使用@Transactional注解管理事务

### 测试
```bash
# 运行单元测试
mvn test

# 运行集成测试
mvn integration-test
```

### 打包部署
```bash
# 打包
mvn clean package

# 运行
java -jar target/mall-backend-1.0.0.jar
```

## 故障排除

### 常见问题

1. **数据库连接失败**
   - 检查数据库服务是否启动
   - 验证数据库连接配置
   - 确认数据库用户权限

2. **JWT验证失败**
   - 检查JWT密钥配置
   - 验证token格式和过期时间

3. **跨域问题**
   - 检查CORS配置
   - 确认前端请求头设置

### 日志查看
```bash
# 查看应用日志
tail -f logs/spring.log

# 查看SQL日志
# 在application.yml中设置：
logging:
  level:
    org.hibernate.SQL: debug
```

## 性能优化

1. **数据库优化**
   - 添加必要的索引
   - 使用分页查询
   - 优化SQL语句

2. **缓存策略**
   - 可集成Redis做缓存
   - 使用Spring Cache

3. **连接池优化**
   - 配置HikariCP连接池参数

## 扩展功能

### 待实现功能
- [ ] 订单管理完整流程
- [ ] 支付集成
- [ ] 文件上传服务
- [ ] 邮件通知
- [ ] 短信验证
- [ ] 数据统计API
- [ ] 管理员后台API

### 技术优化
- [ ] 集成Redis缓存
- [ ] 添加API限流
- [ ] 集成消息队列
- [ ] 添加监控和健康检查
- [ ] 完善单元测试

## 许可证

MIT License
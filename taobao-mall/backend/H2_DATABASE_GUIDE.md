# H2数据库使用指南

## 概述
项目已从MySQL数据库迁移到H2内存数据库，方便开发和测试使用，无需安装和配置外部数据库。

## 配置说明

### 数据库连接配置
- **数据库类型**: H2内存数据库
- **连接URL**: `jdbc:h2:mem:taobao_mall`
- **用户名**: `sa`
- **密码**: 空
- **驱动类**: `org.h2.Driver`

### H2控制台
项目启动后，可以通过H2控制台查看和管理数据库：
- **访问地址**: http://localhost:8080/api/h2-console
- **JDBC URL**: `jdbc:h2:mem:taobao_mall`
- **用户名**: `sa`
- **密码**: 留空

## 数据库特性

### 自动建表
- 使用 `ddl-auto: create-drop` 配置
- 应用启动时自动创建表结构
- 应用关闭时自动删除表结构

### 初始数据
项目包含 `data.sql` 文件，包含以下示例数据：
- 商品分类（电子产品、服装、图书、家居用品）
- 测试用户（admin、testuser）
- 示例商品（iPhone 15 Pro、华为Mate 60 Pro等）
- 用户地址信息
- 购物车数据

### 默认用户账号
- **管理员账号**: 
  - 用户名: `admin`
  - 邮箱: `admin@taobao.com`
  - 密码: `password`（加密后存储）

- **测试用户**:
  - 用户名: `testuser` 
  - 邮箱: `test@taobao.com`
  - 密码: `password`（加密后存储）

## 使用方式

1. **启动应用**
   ```bash
   cd taobao-mall/backend
   mvn spring-boot:run
   ```

2. **访问H2控制台**
   - 打开浏览器访问: http://localhost:8080/api/h2-console
   - 输入连接信息后点击连接

3. **查看数据**
   - 可以执行SQL查询查看表结构和数据
   - 例如: `SELECT * FROM users;`

## 注意事项

1. **数据持久性**: H2内存数据库的数据仅在应用运行期间存在，重启应用后数据会重置
2. **性能**: 内存数据库性能优异，适合开发和测试
3. **迁移**: 如需部署到生产环境，建议改回MySQL或其他持久化数据库

## 实体类兼容性
所有实体类已验证与H2数据库兼容：
- ✅ User（用户）
- ✅ Product（商品）
- ✅ Category（分类）
- ✅ Order（订单）
- ✅ OrderItem（订单项）
- ✅ Cart（购物车）
- ✅ CartItem（购物车项）
- ✅ Address（地址）

## 依赖变更
- 移除: `mysql-connector-java`
- 添加: `h2database`（Spring Boot自动版本管理）

## 🎉 迁移完成状态

### ✅ 已完成的任务
1. **数据库依赖更换**: 将MySQL驱动替换为H2数据库
2. **配置文件更新**: 修改application.yml中的数据源配置
3. **实体类兼容性验证**: 所有JPA实体类与H2完全兼容
4. **数据初始化**: 创建DataInitializer类自动插入示例数据
5. **Repository完善**: 补充了CartItemRepository接口

### 🗄️ 数据库表结构
- `users` - 用户表
- `categories` - 分类表（支持父子级联）
- `products` - 商品表
- `orders` - 订单表
- `order_items` - 订单项表
- `carts` - 购物车表
- `cart_items` - 购物车项表
- `addresses` - 地址表
- `product_images` - 商品图片表

### 📦 示例数据
- 4个主要分类：电子产品、服装、图书、家居用品
- 2个子分类：手机、电脑
- 2个测试用户：admin、testuser
- 4个示例商品：iPhone 15 Pro、华为Mate 60 Pro、MacBook Pro 14、Java编程思想
- 1个收货地址
- 购物车数据（包含2个商品）

### 🚀 启动验证
应用启动成功，所有功能正常：
- ✅ 数据库连接正常
- ✅ 表结构自动创建
- ✅ 示例数据插入成功
- ✅ H2控制台可访问
- ✅ Web服务器启动正常
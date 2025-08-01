# 淘宝商城 - 全栈电商平台

这是一个完整的电商平台，包含前端和后端，采用现代化的技术栈构建。

## 技术栈

### 后端
- **Java 17** - 编程语言
- **Spring Boot 3.2.0** - 后端框架
- **Spring Data JPA** - 数据访问层
- **Spring Security** - 安全框架
- **MySQL** - 数据库
- **JWT** - 身份认证
- **Maven** - 项目管理

### 前端
- **Vue.js 3** - 前端框架
- **Vite** - 构建工具
- **Element Plus** - UI组件库
- **Pinia** - 状态管理
- **Vue Router** - 路由管理
- **Axios** - HTTP客户端

## 功能特性

### 用户功能
- ✅ 用户注册/登录
- ✅ 商品浏览和搜索
- ✅ 商品详情查看
- ✅ 购物车管理
- ✅ 分类浏览
- ⏳ 订单管理
- ⏳ 地址管理
- ⏳ 个人信息管理

### 管理功能
- ⏳ 商品管理
- ⏳ 订单管理
- ⏳ 用户管理
- ⏳ 分类管理

### 系统特性
- 📱 响应式设计
- 🔐 JWT身份认证
- 🎨 现代化UI设计
- 🚀 高性能
- 📊 RESTful API

## 项目结构

```
taobao-mall/
├── backend/                 # 后端项目
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/taobao/mall/
│   │       │       ├── controller/    # 控制器层
│   │       │       ├── service/       # 服务层
│   │       │       ├── repository/    # 数据访问层
│   │       │       ├── entity/        # 实体类
│   │       │       ├── dto/           # 数据传输对象
│   │       │       └── config/        # 配置类
│   │       └── resources/
│   │           └── application.yml    # 配置文件
│   └── pom.xml                        # Maven配置
├── frontend/                # 前端项目
│   ├── src/
│   │   ├── components/      # Vue组件
│   │   ├── views/           # 页面组件
│   │   ├── stores/          # Pinia状态管理
│   │   ├── utils/           # 工具函数
│   │   ├── assets/          # 静态资源
│   │   └── router/          # 路由配置
│   ├── package.json         # NPM配置
│   └── vite.config.js       # Vite配置
└── README.md                # 项目文档
```

## 快速开始

### 环境要求
- Java 17+
- Node.js 16+
- MySQL 8.0+
- Maven 3.6+

### 后端启动

1. 进入后端目录：
```bash
cd taobao-mall/backend
```

2. 配置数据库：
   - 创建MySQL数据库 `taobao_mall`
   - 修改 `src/main/resources/application.yml` 中的数据库配置

3. 安装依赖并启动：
```bash
mvn clean install
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

### 前端启动

1. 进入前端目录：
```bash
cd taobao-mall/frontend
```

2. 安装依赖：
```bash
npm install
```

3. 启动开发服务器：
```bash
npm run dev
```

前端应用将在 `http://localhost:3000` 启动

## API文档

### 认证接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录

### 商品接口
- `GET /api/products` - 获取商品列表
- `GET /api/products/{id}` - 获取商品详情
- `GET /api/products/search` - 搜索商品
- `GET /api/products/hot` - 获取热门商品
- `GET /api/products/latest` - 获取最新商品

### 分类接口
- `GET /api/categories` - 获取所有分类
- `GET /api/categories/root` - 获取根分类
- `GET /api/categories/{id}` - 获取分类详情

### 购物车接口
- `GET /api/cart` - 获取购物车
- `POST /api/cart/add` - 添加商品到购物车
- `PUT /api/cart/update` - 更新购物车商品数量
- `DELETE /api/cart/remove/{productId}` - 移除购物车商品
- `DELETE /api/cart/clear` - 清空购物车

## 数据库设计

### 核心表结构
- `users` - 用户表
- `products` - 商品表
- `categories` - 分类表
- `orders` - 订单表
- `order_items` - 订单项表
- `carts` - 购物车表
- `cart_items` - 购物车项表
- `addresses` - 地址表

## 部署说明

### 后端部署
1. 打包应用：
```bash
mvn clean package
```

2. 运行JAR文件：
```bash
java -jar target/mall-backend-1.0.0.jar
```

### 前端部署
1. 构建生产版本：
```bash
npm run build
```

2. 将 `dist` 目录部署到Web服务器

## 开发计划

### 短期目标
- [ ] 完善订单管理功能
- [ ] 实现支付功能
- [ ] 添加商品评价系统
- [ ] 实现地址管理

### 长期目标
- [ ] 添加管理后台
- [ ] 实现优惠券系统
- [ ] 添加商品推荐算法
- [ ] 移动端适配优化

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 项目地址：[GitHub](https://github.com/your-username/taobao-mall)
- 问题反馈：[Issues](https://github.com/your-username/taobao-mall/issues)

## 致谢

感谢所有为这个项目做出贡献的开发者们！

---

© 2024 淘宝商城. All rights reserved.
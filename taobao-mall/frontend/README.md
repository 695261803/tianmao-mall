# 淘宝商城前端

基于Vue 3的现代化电商平台前端应用。

## 技术栈

- **Vue.js 3** - 渐进式JavaScript框架
- **Vite** - 快速构建工具
- **Element Plus** - Vue 3 UI组件库
- **Pinia** - 状态管理
- **Vue Router** - 路由管理
- **Axios** - HTTP客户端
- **JavaScript ES6+** - 编程语言

## 功能特性

- 🎨 现代化UI设计，响应式布局
- 🛒 完整的购物流程
- 🔍 商品搜索和筛选
- 📱 移动端适配
- 🚀 快速加载和流畅动画
- 🔐 用户认证和状态管理

## 项目结构

```
src/
├── components/          # 可复用组件
│   ├── Header.vue      # 页面头部
│   ├── Footer.vue      # 页面底部
│   └── ProductCard.vue # 商品卡片
├── views/              # 页面组件
│   ├── Home.vue        # 首页
│   ├── Products.vue    # 商品列表
│   ├── ProductDetail.vue # 商品详情
│   ├── Cart.vue        # 购物车
│   ├── Login.vue       # 登录页
│   └── Register.vue    # 注册页
├── stores/             # Pinia状态管理
│   ├── user.js         # 用户状态
│   └── cart.js         # 购物车状态
├── utils/              # 工具函数
│   └── api.js          # API请求封装
├── assets/             # 静态资源
│   └── css/
│       └── style.css   # 全局样式
├── router/             # 路由配置
│   └── index.js
├── App.vue             # 根组件
└── main.js             # 应用入口
```

## 快速开始

### 环境要求
- Node.js 16+
- npm 或 yarn

### 安装启动

1. 克隆项目：
```bash
git clone <repository-url>
cd taobao-mall/frontend
```

2. 安装依赖：
```bash
npm install
# 或
yarn install
```

3. 启动开发服务器：
```bash
npm run dev
# 或
yarn dev
```

应用将在 `http://localhost:3000` 启动

### 构建生产版本

```bash
npm run build
# 或
yarn build
```

构建文件将在 `dist` 目录中生成。

## 页面功能

### 首页 (Home)
- 轮播图展示
- 商品分类导航
- 热门商品推荐
- 最新商品展示

### 商品列表 (Products)
- 商品网格展示
- 分类筛选
- 价格筛选
- 搜索功能
- 排序选项
- 分页加载

### 商品详情 (ProductDetail)
- 商品图片展示
- 详细信息
- 规格选择
- 数量选择
- 加入购物车
- 立即购买

### 购物车 (Cart)
- 商品列表
- 数量修改
- 商品删除
- 总价计算
- 结算功能

### 用户认证
- 用户注册
- 用户登录
- 状态保持
- 自动跳转

## 组件说明

### Header组件
- Logo和网站名称
- 搜索栏
- 导航菜单
- 用户信息/登录按钮
- 购物车图标和数量

### ProductCard组件
- 商品图片
- 商品信息
- 价格显示
- 快速加入购物车
- 悬停效果

### Footer组件
- 网站信息
- 联系方式
- 服务链接
- 版权信息

## 状态管理

### 用户状态 (stores/user.js)
```javascript
const userStore = useUserStore()

// 登录
await userStore.login(credentials)

// 注册
await userStore.register(userData)

// 登出
userStore.logout()

// 检查认证状态
userStore.isAuthenticated
```

### 购物车状态 (stores/cart.js)
```javascript
const cartStore = useCartStore()

// 添加商品
await cartStore.addToCart(productId, quantity)

// 更新数量
await cartStore.updateQuantity(productId, quantity)

// 移除商品
await cartStore.removeItem(productId)

// 获取购物车总数
cartStore.itemCount

// 获取总价
cartStore.totalPrice
```

## API集成

### API配置 (utils/api.js)
```javascript
import api from '@/utils/api'

// GET请求
const response = await api.get('/products')

// POST请求
const response = await api.post('/auth/login', data)

// 带认证的请求
// Token会自动添加到请求头
```

### 请求拦截器
- 自动添加JWT Token
- 请求格式统一处理

### 响应拦截器
- 统一错误处理
- 401自动跳转登录
- 数据格式标准化

## 样式设计

### 设计理念
- 简洁现代的界面设计
- 一致的视觉语言
- 良好的用户体验

### 色彩方案
- 主色调：渐变紫色 `#667eea` → `#764ba2`
- 强调色：红色 `#ff6b6b`
- 文字色：深灰 `#333`
- 背景色：浅灰 `#f5f5f5`

### 响应式设计
```css
/* 平板设备 */
@media (max-width: 768px) {
  /* 样式调整 */
}

/* 手机设备 */
@media (max-width: 480px) {
  /* 样式调整 */
}
```

## 开发指南

### 代码规范
- 使用Vue 3 Composition API
- 组件名使用PascalCase
- 文件名使用kebab-case
- 使用ESLint进行代码检查

### 组件开发
```vue
<template>
  <div class="component-name">
    <!-- 模板内容 -->
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'

// 组合式API逻辑
</script>

<style scoped>
/* 组件样式 */
</style>
```

### 路由配置
```javascript
// router/index.js
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  // 其他路由
]
```

## 性能优化

### 代码分割
- 路由懒加载
- 组件异步加载

### 图片优化
- 懒加载图片
- 响应式图片
- WebP格式支持

### 缓存策略
- 静态资源缓存
- API数据缓存
- 路由缓存

## 部署

### 构建配置
```javascript
// vite.config.js
export default defineConfig({
  base: '/taobao-mall/',  // 部署路径
  build: {
    outDir: 'dist',
    assetsDir: 'assets'
  }
})
```

### 静态部署
```bash
# 构建
npm run build

# 部署到静态服务器
cp -r dist/* /var/www/html/
```

### Docker部署
```dockerfile
FROM nginx:alpine
COPY dist/ /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## 浏览器支持

- Chrome >= 87
- Firefox >= 78
- Safari >= 14
- Edge >= 88

## 开发工具

### 推荐VSCode插件
- Vetur / Volar
- ESLint
- Prettier
- Vue 3 Snippets

### 开发命令
```bash
# 开发服务器
npm run dev

# 构建
npm run build

# 预览构建结果
npm run preview

# 代码检查
npm run lint

# 代码格式化
npm run format
```

## 常见问题

### 开发环境问题

1. **端口冲突**
```bash
# 修改端口
npm run dev -- --port 3001
```

2. **代理配置**
```javascript
// vite.config.js
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

3. **CORS问题**
- 确保后端配置了正确的CORS设置
- 检查请求头设置

### 构建问题

1. **路径问题**
```javascript
// 确保base路径正确
base: process.env.NODE_ENV === 'production' ? '/taobao-mall/' : '/'
```

2. **资源加载问题**
- 检查静态资源路径
- 确认服务器配置

## 扩展功能

### 待实现功能
- [ ] 用户个人中心
- [ ] 订单历史
- [ ] 地址管理
- [ ] 商品收藏
- [ ] 商品评价
- [ ] 在线客服
- [ ] 多语言支持
- [ ] 暗黑模式

### 技术优化
- [ ] 服务端渲染(SSR)
- [ ] 渐进式Web应用(PWA)
- [ ] 性能监控
- [ ] 错误追踪
- [ ] A/B测试
- [ ] 国际化(i18n)

## 许可证

MIT License
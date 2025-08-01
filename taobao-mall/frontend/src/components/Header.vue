<template>
  <el-header class="header">
    <div class="header-content">
      <div class="logo" @click="$router.push('/')">
        <el-icon><Shop /></el-icon>
        <span>淘宝商城</span>
      </div>
      
      <div class="search-bar">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索商品"
          class="search-input"
          @keyup.enter="handleSearch"
        >
          <template #append>
            <el-button @click="handleSearch" type="primary">
              <el-icon><Search /></el-icon>
            </el-button>
          </template>
        </el-input>
      </div>
      
      <div class="nav-menu">
        <el-menu
          mode="horizontal"
          background-color="transparent"
          text-color="white"
          active-text-color="#ffd04b"
          class="nav-menu-items"
        >
          <el-menu-item index="home" @click="$router.push('/')">首页</el-menu-item>
          <el-menu-item index="products" @click="$router.push('/products')">商品</el-menu-item>
          <el-menu-item index="cart" @click="$router.push('/cart')">
            <el-badge :value="cartStore.itemCount" class="cart-badge">
              <el-icon><ShoppingCart /></el-icon>
              购物车
            </el-badge>
          </el-menu-item>
          
          <el-sub-menu index="user" v-if="userStore.isAuthenticated">
            <template #title>
              <el-icon><User /></el-icon>
              {{ userStore.user?.nickname || userStore.user?.username }}
            </template>
            <el-menu-item @click="handleLogout">退出登录</el-menu-item>
          </el-sub-menu>
          
          <div v-else class="auth-buttons">
            <el-button size="small" @click="$router.push('/login')">登录</el-button>
            <el-button size="small" type="primary" @click="$router.push('/register')">注册</el-button>
          </div>
        </el-menu>
      </div>
    </div>
  </el-header>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { useCartStore } from '../stores/cart'
import { Shop, Search, ShoppingCart, User } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()
const searchKeyword = ref('')

// 初始化用户认证状态
userStore.initializeAuth()

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({
      path: '/products',
      query: { search: searchKeyword.value.trim() }
    })
  }
}

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  height: 70px !important;
  padding: 0;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  display: flex;
  align-items: center;
  color: white;
  font-size: 24px;
  font-weight: bold;
  cursor: pointer;
  user-select: none;
}

.logo .el-icon {
  margin-right: 8px;
  font-size: 28px;
}

.search-bar {
  flex: 1;
  max-width: 500px;
  margin: 0 40px;
}

.search-input {
  width: 100%;
}

.nav-menu {
  display: flex;
  align-items: center;
}

.nav-menu-items {
  border: none;
  background: transparent;
}

.cart-badge {
  margin-right: 5px;
}

.auth-buttons {
  margin-left: 20px;
}

.auth-buttons .el-button {
  margin-left: 8px;
}

@media (max-width: 768px) {
  .header-content {
    padding: 0 10px;
  }
  
  .search-bar {
    margin: 0 20px;
    max-width: 300px;
  }
  
  .logo span {
    display: none;
  }
}
</style>
<template>
  <div class="home">
    <!-- 轮播图 -->
    <el-carousel class="banner" height="400px" indicator-position="outside">
      <el-carousel-item v-for="(banner, index) in banners" :key="index">
        <div class="banner-item" :style="{ backgroundImage: `url(${banner.image})` }">
          <div class="banner-content">
            <h2>{{ banner.title }}</h2>
            <p>{{ banner.description }}</p>
            <el-button type="primary" size="large" @click="$router.push('/products')">
              立即购买
            </el-button>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>

    <!-- 分类导航 -->
    <div class="categories-section">
      <div class="container">
        <h2 class="section-title">商品分类</h2>
        <div class="categories-grid">
          <div
            v-for="category in categories"
            :key="category.id"
            class="category-item"
            @click="goToCategory(category.id)"
          >
            <el-icon class="category-icon"><Box /></el-icon>
            <span>{{ category.name }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 热门商品 -->
    <div class="products-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">热门商品</h2>
          <el-button type="text" @click="$router.push('/products')">
            查看更多 <el-icon><ArrowRight /></el-icon>
          </el-button>
        </div>
        
        <div class="products-grid" v-loading="loading">
          <ProductCard
            v-for="product in hotProducts"
            :key="product.id"
            :product="product"
          />
        </div>
      </div>
    </div>

    <!-- 最新商品 -->
    <div class="products-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">最新商品</h2>
          <el-button type="text" @click="$router.push('/products')">
            查看更多 <el-icon><ArrowRight /></el-icon>
          </el-button>
        </div>
        
        <div class="products-grid" v-loading="loading">
          <ProductCard
            v-for="product in latestProducts"
            :key="product.id"
            :product="product"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import api from '../utils/api'
import { Box, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()
const loading = ref(false)
const categories = ref([])
const hotProducts = ref([])
const latestProducts = ref([])

const banners = ref([
  {
    title: '春季新品上市',
    description: '精选春季新品，折扣高达50%',
    image: 'https://via.placeholder.com/1200x400/667eea/ffffff?text=Spring+Collection'
  },
  {
    title: '电子产品专场',
    description: '最新科技产品，品质保证',
    image: 'https://via.placeholder.com/1200x400/764ba2/ffffff?text=Electronics'
  },
  {
    title: '家居生活',
    description: '打造舒适家居环境',
    image: 'https://via.placeholder.com/1200x400/f093fb/ffffff?text=Home+Living'
  }
])

const goToCategory = (categoryId) => {
  router.push(`/products?category=${categoryId}`)
}

const fetchCategories = async () => {
  try {
    const response = await api.get('/categories/root')
    if (response.code === 200) {
      categories.value = response.data.slice(0, 8) // 只显示前8个分类
    }
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

const fetchHotProducts = async () => {
  try {
    loading.value = true
    const response = await api.get('/products/hot?size=8')
    if (response.code === 200) {
      hotProducts.value = response.data.content || []
    }
  } catch (error) {
    console.error('获取热门商品失败:', error)
  } finally {
    loading.value = false
  }
}

const fetchLatestProducts = async () => {
  try {
    loading.value = true
    const response = await api.get('/products/latest?size=8')
    if (response.code === 200) {
      latestProducts.value = response.data.content || []
    }
  } catch (error) {
    console.error('获取最新商品失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCategories()
  fetchHotProducts()
  fetchLatestProducts()
})
</script>

<style scoped>
.home {
  min-height: calc(100vh - 140px);
}

.banner {
  margin-bottom: 60px;
}

.banner-item {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.banner-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
}

.banner-content {
  text-align: center;
  color: white;
  z-index: 1;
  position: relative;
}

.banner-content h2 {
  font-size: 48px;
  margin-bottom: 16px;
  font-weight: bold;
}

.banner-content p {
  font-size: 20px;
  margin-bottom: 24px;
}

.categories-section, .products-section {
  padding: 60px 0;
}

.categories-section {
  background-color: white;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.section-title {
  font-size: 32px;
  font-weight: bold;
  color: #333;
  margin-bottom: 40px;
  text-align: center;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40px;
}

.section-header .section-title {
  margin-bottom: 0;
  text-align: left;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
}

.category-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
}

.category-item:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.category-icon {
  font-size: 48px;
  color: #667eea;
  margin-bottom: 12px;
}

.category-item span {
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

@media (max-width: 768px) {
  .banner-content h2 {
    font-size: 32px;
  }
  
  .banner-content p {
    font-size: 16px;
  }
  
  .categories-grid {
    grid-template-columns: repeat(4, 1fr);
    gap: 15px;
  }
  
  .category-item {
    padding: 20px 10px;
  }
  
  .category-icon {
    font-size: 36px;
  }
  
  .products-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
  }
}

@media (max-width: 480px) {
  .categories-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .products-grid {
    grid-template-columns: 1fr;
  }
}
</style>
<template>
  <div class="product-detail-page">
    <div class="container" v-loading="loading">
      <div v-if="product" class="product-detail">
        <div class="product-images">
          <div class="main-image">
            <img :src="currentImage" :alt="product.name" />
          </div>
          <div class="image-thumbnails" v-if="product.images && product.images.length > 1">
            <img
              v-for="(image, index) in product.images"
              :key="index"
              :src="image"
              :alt="`${product.name} - ${index + 1}`"
              :class="{ active: currentImage === image }"
              @click="currentImage = image"
            />
          </div>
        </div>
        
        <div class="product-info">
          <h1 class="product-title">{{ product.name }}</h1>
          
          <div class="product-price">
            <span class="current-price">¥{{ product.price }}</span>
            <span
              v-if="product.originalPrice && product.originalPrice > product.price"
              class="original-price"
            >
              ¥{{ product.originalPrice }}
            </span>
          </div>
          
          <div class="product-stats">
            <div class="stat-item">
              <span class="label">销量：</span>
              <span class="value">{{ product.sales || 0 }}</span>
            </div>
            <div class="stat-item">
              <span class="label">库存：</span>
              <span class="value">{{ product.stock }}</span>
            </div>
            <div class="stat-item">
              <span class="label">浏览：</span>
              <span class="value">{{ product.views || 0 }}</span>
            </div>
          </div>
          
          <div class="quantity-selector">
            <label>数量：</label>
            <el-input-number
              v-model="quantity"
              :min="1"
              :max="product.stock"
              size="large"
            />
          </div>
          
          <div class="action-buttons">
            <el-button
              type="primary"
              size="large"
              :loading="addingToCart"
              @click="addToCart"
              :disabled="product.stock === 0"
            >
              <el-icon><ShoppingCart /></el-icon>
              {{ product.stock === 0 ? '缺货' : '加入购物车' }}
            </el-button>
            <el-button
              type="danger"
              size="large"
              :disabled="product.stock === 0"
            >
              立即购买
            </el-button>
          </div>
        </div>
      </div>
      
      <div v-if="product" class="product-description">
        <el-card>
          <template #header>
            <h3>商品详情</h3>
          </template>
          <div class="description-content">
            <p>{{ product.description }}</p>
          </div>
        </el-card>
      </div>
      
      <div v-if="!product && !loading" class="not-found">
        <el-result
          icon="warning"
          title="商品不存在"
          sub-title="抱歉，您查看的商品可能已下架或不存在"
        >
          <template #extra>
            <el-button type="primary" @click="$router.push('/')">
              返回首页
            </el-button>
          </template>
        </el-result>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { ElMessage } from 'element-plus'
import { ShoppingCart } from '@element-plus/icons-vue'
import api from '../utils/api'

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()

const loading = ref(false)
const product = ref(null)
const currentImage = ref('')
const quantity = ref(1)
const addingToCart = ref(false)

const fetchProduct = async () => {
  try {
    loading.value = true
    const response = await api.get(`/products/${props.id}`)
    
    if (response.code === 200) {
      product.value = response.data
      currentImage.value = response.data.mainImage || (response.data.images && response.data.images[0]) || '/placeholder.jpg'
    }
  } catch (error) {
    console.error('获取商品详情失败:', error)
    ElMessage.error('获取商品详情失败')
  } finally {
    loading.value = false
  }
}

const addToCart = async () => {
  try {
    addingToCart.value = true
    await cartStore.addToCart(product.value.id, quantity.value)
    ElMessage.success(`已添加 ${quantity.value} 件商品到购物车`)
  } catch (error) {
    ElMessage.error(error.message || '添加失败，请先登录')
  } finally {
    addingToCart.value = false
  }
}

onMounted(() => {
  fetchProduct()
})
</script>

<style scoped>
.product-detail-page {
  min-height: calc(100vh - 140px);
  background-color: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.product-detail {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.product-images {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.main-image {
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid #eee;
}

.main-image img {
  width: 100%;
  height: 400px;
  object-fit: cover;
}

.image-thumbnails {
  display: flex;
  gap: 8px;
  overflow-x: auto;
}

.image-thumbnails img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
  border: 2px solid transparent;
  cursor: pointer;
  transition: all 0.3s ease;
}

.image-thumbnails img:hover,
.image-thumbnails img.active {
  border-color: #667eea;
}

.product-info {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.product-title {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin: 0;
  line-height: 1.4;
}

.product-price {
  display: flex;
  align-items: center;
  gap: 16px;
}

.current-price {
  font-size: 32px;
  font-weight: bold;
  color: #ff6b6b;
}

.original-price {
  font-size: 20px;
  color: #999;
  text-decoration: line-through;
}

.product-stats {
  display: flex;
  gap: 24px;
  padding: 16px 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 4px;
}

.stat-item .label {
  color: #666;
  font-size: 14px;
}

.stat-item .value {
  color: #333;
  font-weight: 500;
}

.quantity-selector {
  display: flex;
  align-items: center;
  gap: 12px;
}

.quantity-selector label {
  font-size: 16px;
  font-weight: 500;
  color: #333;
}

.action-buttons {
  display: flex;
  gap: 16px;
}

.action-buttons .el-button {
  flex: 1;
  height: 50px;
  font-size: 16px;
  font-weight: 500;
}

.product-description {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.description-content {
  line-height: 1.8;
  color: #666;
}

.not-found {
  background: white;
  border-radius: 12px;
  padding: 40px;
  text-align: center;
}

@media (max-width: 768px) {
  .product-detail {
    grid-template-columns: 1fr;
    gap: 20px;
    padding: 20px;
  }
  
  .main-image img {
    height: 300px;
  }
  
  .product-title {
    font-size: 22px;
  }
  
  .current-price {
    font-size: 24px;
  }
  
  .action-buttons {
    flex-direction: column;
  }
}
</style>
<template>
  <el-card class="product-card" @click="goToDetail">
    <div class="product-image-container">
      <img
        :src="product.mainImage || '/placeholder.jpg'"
        :alt="product.name"
        class="product-image"
      />
      <div class="product-actions">
        <el-button
          type="primary"
          size="small"
          @click.stop="addToCart"
          :loading="addingToCart"
        >
          <el-icon><ShoppingCart /></el-icon>
          加入购物车
        </el-button>
      </div>
    </div>
    
    <div class="product-info">
      <h3 class="product-title">{{ product.name }}</h3>
      <p class="product-description">{{ product.description }}</p>
      
      <div class="product-price-container">
        <span class="product-price">¥{{ product.price }}</span>
        <span
          v-if="product.originalPrice && product.originalPrice > product.price"
          class="original-price"
        >
          ¥{{ product.originalPrice }}
        </span>
      </div>
      
      <div class="product-stats">
        <span class="sales">销量: {{ product.sales || 0 }}</span>
        <span class="stock">库存: {{ product.stock }}</span>
      </div>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { ElMessage } from 'element-plus'
import { ShoppingCart } from '@element-plus/icons-vue'

const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})

const router = useRouter()
const cartStore = useCartStore()
const addingToCart = ref(false)

const goToDetail = () => {
  router.push(`/product/${props.product.id}`)
}

const addToCart = async () => {
  try {
    addingToCart.value = true
    await cartStore.addToCart(props.product.id, 1)
    ElMessage.success('已添加到购物车')
  } catch (error) {
    ElMessage.error(error.message || '添加失败')
  } finally {
    addingToCart.value = false
  }
}
</script>

<style scoped>
.product-card {
  height: 400px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 12px;
  overflow: hidden;
}

.product-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
}

.product-image-container {
  position: relative;
  height: 220px;
  overflow: hidden;
}

.product-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.product-card:hover .product-image {
  transform: scale(1.05);
}

.product-actions {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.product-card:hover .product-actions {
  opacity: 1;
}

.product-info {
  padding: 16px;
  height: 180px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.product-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0 0 8px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-description {
  font-size: 12px;
  color: #666;
  margin: 0 0 12px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-price-container {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.product-price {
  font-size: 20px;
  font-weight: bold;
  color: #ff6b6b;
}

.original-price {
  font-size: 14px;
  color: #999;
  text-decoration: line-through;
  margin-left: 8px;
}

.product-stats {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #999;
}

@media (max-width: 768px) {
  .product-card {
    height: 350px;
  }
  
  .product-image-container {
    height: 180px;
  }
  
  .product-info {
    height: 170px;
    padding: 12px;
  }
}
</style>
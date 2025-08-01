<template>
  <div class="cart-page">
    <div class="container">
      <div class="cart-header">
        <h1>购物车</h1>
        <el-button
          v-if="cartStore.items.length > 0"
          type="danger"
          @click="clearCart"
        >
          清空购物车
        </el-button>
      </div>
      
      <div v-loading="loading" class="cart-content">
        <div v-if="cartStore.items.length === 0" class="empty-cart">
          <el-empty description="购物车为空">
            <el-button type="primary" @click="$router.push('/products')">
              去购物
            </el-button>
          </el-empty>
        </div>
        
        <div v-else class="cart-items">
          <el-card v-for="item in cartStore.items" :key="item.id" class="cart-item">
            <div class="item-content">
              <div class="item-image">
                <img
                  :src="item.product.mainImage || '/placeholder.jpg'"
                  :alt="item.product.name"
                />
              </div>
              
              <div class="item-info">
                <h3 class="item-title" @click="goToProduct(item.product.id)">
                  {{ item.product.name }}
                </h3>
                <p class="item-description">{{ item.product.description }}</p>
                
                <div class="item-price">
                  <span class="current-price">¥{{ item.product.price }}</span>
                  <span
                    v-if="item.product.originalPrice && item.product.originalPrice > item.product.price"
                    class="original-price"
                  >
                    ¥{{ item.product.originalPrice }}
                  </span>
                </div>
              </div>
              
              <div class="item-controls">
                <div class="quantity-control">
                  <label>数量：</label>
                  <el-input-number
                    :model-value="item.quantity"
                    :min="1"
                    :max="item.product.stock"
                    @change="updateQuantity(item.product.id, $event)"
                  />
                </div>
                
                <div class="item-total">
                  <span class="total-label">小计：</span>
                  <span class="total-price">¥{{ (item.product.price * item.quantity).toFixed(2) }}</span>
                </div>
                
                <el-button
                  type="danger"
                  text
                  @click="removeItem(item.product.id)"
                >
                  <el-icon><Delete /></el-icon>
                  删除
                </el-button>
              </div>
            </div>
          </el-card>
        </div>
        
        <div v-if="cartStore.items.length > 0" class="cart-summary">
          <el-card class="summary-card">
            <div class="summary-content">
              <div class="summary-row">
                <span>商品数量：</span>
                <span>{{ cartStore.itemCount }} 件</span>
              </div>
              <div class="summary-row total-row">
                <span>总金额：</span>
                <span class="total-amount">¥{{ cartStore.totalPrice.toFixed(2) }}</span>
              </div>
              <el-button
                type="primary"
                size="large"
                class="checkout-button"
                @click="checkout"
              >
                结算
              </el-button>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useUserStore } from '../stores/user'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'

const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()
const loading = ref(false)

const goToProduct = (productId) => {
  router.push(`/product/${productId}`)
}

const updateQuantity = async (productId, quantity) => {
  try {
    await cartStore.updateQuantity(productId, quantity)
    ElMessage.success('更新成功')
  } catch (error) {
    ElMessage.error(error.message || '更新失败')
  }
}

const removeItem = async (productId) => {
  try {
    await ElMessageBox.confirm('确定要删除这件商品吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await cartStore.removeItem(productId)
    ElMessage.success('删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '删除失败')
    }
  }
}

const clearCart = async () => {
  try {
    await ElMessageBox.confirm('确定要清空购物车吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await cartStore.clearCart()
    ElMessage.success('购物车已清空')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '清空失败')
    }
  }
}

const checkout = () => {
  if (!userStore.isAuthenticated) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  
  ElMessage.info('结算功能暂未实现')
}

const fetchCart = async () => {
  if (!userStore.isAuthenticated) {
    return
  }
  
  try {
    loading.value = true
    await cartStore.fetchCart()
  } catch (error) {
    console.error('获取购物车失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchCart()
})
</script>

<style scoped>
.cart-page {
  min-height: calc(100vh - 140px);
  background-color: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.cart-header h1 {
  font-size: 28px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.cart-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
  align-items: start;
}

.empty-cart {
  grid-column: 1 / -1;
  background: white;
  border-radius: 12px;
  padding: 60px;
  text-align: center;
}

.cart-items {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.cart-item {
  border-radius: 12px;
  overflow: hidden;
}

.item-content {
  display: grid;
  grid-template-columns: auto 1fr auto;
  gap: 20px;
  align-items: center;
}

.item-image {
  width: 100px;
  height: 100px;
  border-radius: 8px;
  overflow: hidden;
}

.item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item-info {
  flex: 1;
}

.item-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 8px 0;
  cursor: pointer;
  transition: color 0.3s ease;
}

.item-title:hover {
  color: #667eea;
}

.item-description {
  font-size: 14px;
  color: #666;
  margin: 0 0 12px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-price {
  display: flex;
  align-items: center;
  gap: 8px;
}

.current-price {
  font-size: 18px;
  font-weight: bold;
  color: #ff6b6b;
}

.original-price {
  font-size: 14px;
  color: #999;
  text-decoration: line-through;
}

.item-controls {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 12px;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 8px;
}

.quantity-control label {
  font-size: 14px;
  color: #666;
}

.item-total {
  display: flex;
  align-items: center;
  gap: 8px;
}

.total-label {
  font-size: 14px;
  color: #666;
}

.total-price {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.cart-summary {
  position: sticky;
  top: 20px;
}

.summary-card {
  border-radius: 12px;
}

.summary-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
}

.total-row {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  padding-top: 16px;
  border-top: 1px solid #eee;
}

.total-amount {
  color: #ff6b6b;
  font-size: 24px;
}

.checkout-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 500;
  margin-top: 8px;
}

@media (max-width: 768px) {
  .cart-content {
    grid-template-columns: 1fr;
  }
  
  .item-content {
    grid-template-columns: auto 1fr;
    gap: 12px;
  }
  
  .item-controls {
    grid-column: 1 / -1;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-top: 12px;
    padding-top: 12px;
    border-top: 1px solid #eee;
  }
  
  .item-image {
    width: 80px;
    height: 80px;
  }
}
</style>
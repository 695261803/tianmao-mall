<template>
  <div class="products-page">
    <div class="container">
      <!-- 筛选栏 -->
      <div class="filters-section">
        <div class="filters-row">
          <div class="filter-group">
            <label>分类：</label>
            <el-select
              v-model="filters.categoryId"
              placeholder="选择分类"
              clearable
              @change="handleFilterChange"
            >
              <el-option
                v-for="category in categories"
                :key="category.id"
                :label="category.name"
                :value="category.id"
              />
            </el-select>
          </div>
          
          <div class="filter-group">
            <label>价格：</label>
            <el-input
              v-model="filters.minPrice"
              placeholder="最低价"
              style="width: 100px"
              @change="handleFilterChange"
            />
            <span style="margin: 0 8px">-</span>
            <el-input
              v-model="filters.maxPrice"
              placeholder="最高价"
              style="width: 100px"
              @change="handleFilterChange"
            />
          </div>
          
          <div class="filter-group">
            <label>排序：</label>
            <el-select
              v-model="sortBy"
              @change="handleSortChange"
            >
              <el-option label="默认" value="id,desc" />
              <el-option label="价格低到高" value="price,asc" />
              <el-option label="价格高到低" value="price,desc" />
              <el-option label="销量高到低" value="sales,desc" />
              <el-option label="最新发布" value="createdAt,desc" />
            </el-select>
          </div>
        </div>
      </div>
      
      <!-- 商品列表 -->
      <div class="products-content" v-loading="loading">
        <div v-if="products.length === 0 && !loading" class="empty-state">
          <el-empty description="暂无商品" />
        </div>
        
        <div v-else class="products-grid">
          <ProductCard
            v-for="product in products"
            :key="product.id"
            :product="product"
          />
        </div>
        
        <!-- 分页 -->
        <div class="pagination-container" v-if="totalPages > 1">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[12, 24, 36, 48]"
            :total="totalElements"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handlePageChange"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import ProductCard from '../components/ProductCard.vue'
import api from '../utils/api'

const route = useRoute()
const loading = ref(false)
const products = ref([])
const categories = ref([])
const currentPage = ref(1)
const pageSize = ref(12)
const totalPages = ref(0)
const totalElements = ref(0)
const sortBy = ref('id,desc')

const filters = reactive({
  categoryId: null,
  minPrice: '',
  maxPrice: '',
  search: ''
})

// 监听路由变化，更新搜索关键词
watch(() => route.query, (newQuery) => {
  filters.search = newQuery.search || ''
  filters.categoryId = newQuery.category ? parseInt(newQuery.category) : null
  currentPage.value = 1
  fetchProducts()
}, { immediate: true })

const fetchProducts = async () => {
  try {
    loading.value = true
    
    let url = '/products'
    const params = new URLSearchParams()
    
    // 处理搜索
    if (filters.search) {
      url = '/products/search'
      params.append('keyword', filters.search)
    }
    
    // 处理分类筛选
    if (filters.categoryId) {
      url = `/products/category/${filters.categoryId}`
    }
    
    // 处理价格筛选
    if (filters.minPrice && filters.maxPrice) {
      url = '/products/price-range'
      params.append('minPrice', filters.minPrice)
      params.append('maxPrice', filters.maxPrice)
    }
    
    // 分页和排序参数
    params.append('page', currentPage.value - 1)
    params.append('size', pageSize.value)
    
    const [sortField, sortDir] = sortBy.value.split(',')
    params.append('sortBy', sortField)
    params.append('sortDir', sortDir)
    
    const response = await api.get(`${url}?${params.toString()}`)
    
    if (response.code === 200) {
      const pageData = response.data
      products.value = pageData.content || []
      totalPages.value = pageData.totalPages || 0
      totalElements.value = pageData.totalElements || 0
    }
  } catch (error) {
    console.error('获取商品失败:', error)
  } finally {
    loading.value = false
  }
}

const fetchCategories = async () => {
  try {
    const response = await api.get('/categories/root')
    if (response.code === 200) {
      categories.value = response.data
    }
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

const handleFilterChange = () => {
  currentPage.value = 1
  fetchProducts()
}

const handleSortChange = () => {
  currentPage.value = 1
  fetchProducts()
}

const handlePageChange = () => {
  fetchProducts()
}

const handleSizeChange = () => {
  currentPage.value = 1
  fetchProducts()
}

onMounted(() => {
  fetchCategories()
})
</script>

<style scoped>
.products-page {
  min-height: calc(100vh - 140px);
  background-color: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.filters-section {
  background: white;
  padding: 20px;
  border-radius: 12px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.filters-row {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-wrap: wrap;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filter-group label {
  font-weight: 500;
  color: #333;
  white-space: nowrap;
}

.products-content {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
  margin-bottom: 40px;
}

.empty-state {
  text-align: center;
  padding: 60px 0;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

@media (max-width: 768px) {
  .filters-row {
    flex-direction: column;
    align-items: stretch;
    gap: 15px;
  }
  
  .filter-group {
    justify-content: space-between;
  }
  
  .products-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
  }
}

@media (max-width: 480px) {
  .products-grid {
    grid-template-columns: 1fr;
  }
}
</style>
import { defineStore } from 'pinia'
import api from '../utils/api'

export const useCartStore = defineStore('cart', {
  state: () => ({
    cart: null,
    items: [],
    total: 0
  }),
  
  getters: {
    itemCount: (state) => state.items.reduce((sum, item) => sum + item.quantity, 0),
    totalPrice: (state) => state.items.reduce((sum, item) => sum + (item.product.price * item.quantity), 0)
  },
  
  actions: {
    async fetchCart() {
      try {
        const response = await api.get('/cart')
        if (response.code === 200) {
          this.cart = response.data
          this.items = response.data.cartItems || []
        }
      } catch (error) {
        console.error('获取购物车失败:', error)
      }
    },
    
    async addToCart(productId, quantity = 1) {
      try {
        const response = await api.post('/cart/add', { productId, quantity })
        if (response.code === 200) {
          this.cart = response.data
          this.items = response.data.cartItems || []
          return response
        }
        throw new Error(response.message)
      } catch (error) {
        throw error
      }
    },
    
    async updateQuantity(productId, quantity) {
      try {
        const response = await api.put('/cart/update', { productId, quantity })
        if (response.code === 200) {
          this.cart = response.data
          this.items = response.data.cartItems || []
          return response
        }
        throw new Error(response.message)
      } catch (error) {
        throw error
      }
    },
    
    async removeItem(productId) {
      try {
        const response = await api.delete(`/cart/remove/${productId}`)
        if (response.code === 200) {
          this.cart = response.data
          this.items = response.data.cartItems || []
          return response
        }
        throw new Error(response.message)
      } catch (error) {
        throw error
      }
    },
    
    async clearCart() {
      try {
        const response = await api.delete('/cart/clear')
        if (response.code === 200) {
          this.items = []
          this.cart = null
          return response
        }
        throw new Error(response.message)
      } catch (error) {
        throw error
      }
    }
  }
})
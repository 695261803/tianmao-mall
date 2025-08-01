import { defineStore } from 'pinia'
import api from '../utils/api'

export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || null,
    isLoggedIn: false
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.token
  },
  
  actions: {
    async login(credentials) {
      try {
        const response = await api.post('/auth/login', credentials)
        if (response.code === 200) {
          this.token = response.data.token
          this.user = response.data.user
          this.isLoggedIn = true
          localStorage.setItem('token', this.token)
          localStorage.setItem('user', JSON.stringify(this.user))
          return response
        }
        throw new Error(response.message)
      } catch (error) {
        throw error
      }
    },
    
    async register(userData) {
      try {
        const response = await api.post('/auth/register', userData)
        if (response.code === 200) {
          this.token = response.data.token
          this.user = response.data.user
          this.isLoggedIn = true
          localStorage.setItem('token', this.token)
          localStorage.setItem('user', JSON.stringify(this.user))
          return response
        }
        throw new Error(response.message)
      } catch (error) {
        throw error
      }
    },
    
    logout() {
      this.user = null
      this.token = null
      this.isLoggedIn = false
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    },
    
    initializeAuth() {
      const token = localStorage.getItem('token')
      const user = localStorage.getItem('user')
      if (token && user) {
        this.token = token
        this.user = JSON.parse(user)
        this.isLoggedIn = true
      }
    }
  }
})
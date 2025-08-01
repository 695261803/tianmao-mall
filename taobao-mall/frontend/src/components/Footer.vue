<template>
  <el-footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h3>关于我们</h3>
        <p>淘宝商城是一个现代化的电商平台，为您提供优质的购物体验。</p>
      </div>
      
      <div class="footer-section">
        <h3>客户服务</h3>
        <ul>
          <li><a href="#">帮助中心</a></li>
          <li><a href="#">售后服务</a></li>
          <li><a href="#">退换货政策</a></li>
          <li><a href="#">配送信息</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h3>购物指南</h3>
        <ul>
          <li><a href="#">新手指南</a></li>
          <li><a href="#">支付方式</a></li>
          <li><a href="#">会员权益</a></li>
          <li><a href="#">积分规则</a></li>
        </ul>
      </div>
      
      <div class="footer-section">
        <h3>联系我们</h3>
        <ul>
          <li><el-icon><Phone /></el-icon> 400-888-8888</li>
          <li><el-icon><Message /></el-icon> service@taobao.com</li>
          <li><el-icon><Clock /></el-icon> 工作时间: 9:00-18:00</li>
        </ul>
      </div>
    </div>
    
    <div class="footer-bottom">
      <p>&copy; 2024 淘宝商城. All rights reserved.</p>
    </div>
  </el-footer>
</template>

<script setup>
import { Phone, Message, Clock } from '@element-plus/icons-vue'
</script>

<style scoped>
.footer {
  background-color: #2c3e50;
  color: white;
  margin-top: auto;
  padding: 40px 0 0;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 40px;
}

.footer-section h3 {
  color: #fff;
  margin-bottom: 20px;
  font-size: 18px;
  font-weight: bold;
}

.footer-section p {
  color: #bdc3c7;
  line-height: 1.6;
}

.footer-section ul {
  list-style: none;
  padding: 0;
}

.footer-section li {
  margin-bottom: 10px;
  color: #bdc3c7;
  display: flex;
  align-items: center;
}

.footer-section li .el-icon {
  margin-right: 8px;
  color: #3498db;
}

.footer-section a {
  color: #bdc3c7;
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-section a:hover {
  color: #3498db;
}

.footer-bottom {
  border-top: 1px solid #34495e;
  margin-top: 40px;
  padding: 20px 0;
  text-align: center;
  color: #7f8c8d;
}

@media (max-width: 768px) {
  .footer-content {
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
    padding: 0 10px;
  }
}

@media (max-width: 480px) {
  .footer-content {
    grid-template-columns: 1fr;
  }
}
</style>
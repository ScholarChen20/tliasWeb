<!-- index.vue -->
<script setup>
import { ref } from 'vue'
import { loginApi } from '@/api/login'
import { ElMessage, ElLoading } from 'element-plus'
import { useRouter } from 'vue-router'

// 表单数据
const loginForm = ref({
  username: '',
  password: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

// 表单引用
const formRef = ref()

// 路由器
const router = useRouter()

// 登录方法
const login = async () => {
  // 显示加载状态
  const loading = ElLoading.service({
    lock: true,
    text: '正在登录...',
    background: 'rgba(0, 0, 0, 0.5)'
  })

  try {
    await formRef.value.validate() // 验证表单

    const result = await loginApi(loginForm.value)

    if (result.code === 1) {
      // 登录成功
      ElMessage.success('登录成功')

      // 存储用户信息
      localStorage.setItem('loginUser', JSON.stringify(result.data))

      // 跳转到首页
      router.push('/index')
    } else {
      ElMessage.error(result.msg || '登录失败')
    }
  } catch (error) {
    ElMessage.error('登录失败，请检查输入信息')
  } finally {
    // 关闭加载状态
    loading.close()
  }
}

// 重置表单
const clear = () => {
  formRef.value.resetFields()
}
</script>

<template>
  <div id="container">
    <!-- 背景图 -->
    <div class="background">
      <img src="@/assets/bg1.jpg" alt="背景图" class="bg-image" />
    </div>

    <!-- 登录表单 -->
    <div class="login-container">
      <el-card
        class="login-form"
        shadow="hover"
        :style="{
          'backdrop-filter': 'blur(16px)',
          'border-radius': '20px',
          'box-shadow': '0 20px 40px rgba(0, 0, 0, 0.3), 0 0 20px rgba(0, 0, 0, 0.1)',
          'transition': 'all 0.3s ease'
        }"
      >
        <template #header>
          <div class="card-header">
            <span class="title">智能后台管理系统</span>
          </div>
        </template>

        <el-form
          ref="formRef"
          :model="loginForm"
          :rules="rules"
          label-width="80px"
          label-position="right"
          size="large"
          :style="{ 'padding': '30px' }"
        >
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
              clearable
              @keyup.enter="login"
              :style="{
                'border-radius': '12px',
                'border': '1px solid rgba(255, 255, 255, 0.3)',
                'background-color': 'rgba(255, 255, 255, 0.2)',
                'color': '#fff',
                'transition': 'all 0.3s ease'
              }"
            />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
              show-password
              @keyup.enter="login"
              :style="{
                'border-radius': '12px',
                'border': '1px solid rgba(255, 255, 255, 0.3)',
                'background-color': 'rgba(255, 255, 255, 0.2)',
                'color': '#fff',
                'transition': 'all 0.3s ease'
              }"
            />
          </el-form-item>

          <!-- 按钮居中显示 -->
          <!-- 按钮组（修改后） -->
          <el-form-item class="el-form-item__content" style="margin-left: 0">
            <div class="button-group">
              <el-button
                  class="login-button"
                  type="primary"
                  @click="login"
                  :loading="loading"
                  :style="{
        'width': '120px',
        'padding': '12px 24px',
        'font-size': '16px',
        'border-radius': '12px',
        'background-color': '#409eff',
        'border-color': '#409eff',
        'color': 'white',
        'cursor': 'pointer',
        'transition': 'all 0.3s ease'
      }"
              >
                登 录
              </el-button>
              <el-button
                  class="reset-button"
                  type="info"
                  @click="clear"
                  :style="{
        'width': '120px',
        'padding': '12px 24px',
        'font-size': '16px',
        'border-radius': '12px',
        'background-color': 'rgba(255, 255, 255, 0.2)',
        'border-color': 'rgba(255, 255, 255, 0.3)',
        'color': '#fff',
        'cursor': 'pointer',
        'transition': 'all 0.3s ease'
      }"
              >
                重 置
              </el-button>
            </div>
          </el-form-item>
        </el-form>

        <!-- 底部信息 -->
        <div class="footer">
          <p class="copyright">© 2025 智能后台管理系统</p>
          <p class="version">v1.0.0</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
/* 容器样式 */
#container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
  background-color: rgba(0, 0, 0, 0.1);
}

/* 背景图 */
.background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
  filter: brightness(1.1);
}

.bg-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: all 0.5s ease;
}

/* 登录容器 */
.login-container {
  position: relative;
  z-index: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

/* 登录表单卡片 */
.login-form {
  width: 400px;
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3), 0 0 20px rgba(0, 0, 0, 0.1);
  background-color: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;
}

.login-form:hover {
  transform: translateY(-4px);
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.4), 0 0 25px rgba(0, 0, 0, 0.15);
}

/* 卡片头部 */
.card-header {
  font-size: 24px;
  font-weight: bold;
  color: #ffffff;
  text-align: center;
  margin-bottom: 20px;
  padding: 0;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

/* 标题 */
.title {
  font-size: 24px;
  font-family: 'Microsoft YaHei', sans-serif;
  text-align: center;
  margin-bottom: 20px;
  color: #ffffff;
  font-weight: bold;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

/* 输入框样式 */
.el-input__inner {
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  background-color: rgba(255, 255, 255, 0.2);
  color: #fff;
  transition: all 0.3s ease;
}

.el-input__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
  background-color: rgba(255, 255, 255, 0.3);
}

/* 前缀图标 */
.el-input__prefix {
  color: #909399;
}

/* 按钮组样式（修改后） */
.button-group {
  display: flex;
  justify-content: flex-start; /* 左对齐，登录按钮靠左 */
  align-items: center;
  gap: 20px; /* 按钮间的固定间隙 */
  margin-top: 30px;
  padding: 0;
}

/* 登录按钮样式（修改后，无margin-right） */
.login-button {
  width: 120px;
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 12px;
  background-color: #409eff;
  border-color: #409eff;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
}

/* 重置按钮样式（保持不变） */
.reset-button {
  width: 120px;
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 12px;
  background-color: rgba(255, 255, 255, 0.2);
  border-color: rgba(255, 255, 255, 0.3);
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.reset-button:hover {
  background-color: rgba(255, 255, 255, 0.3);
  border-color: rgba(255, 255, 255, 0.5);
  transform: translateY(-1px);
}

/* 底部信息 */
.footer {
  margin-top: 30px;
  text-align: center;
  color: rgba(255, 255, 255, 0.7);
  font-size: 12px;
}

.copyright {
  margin-bottom: 5px;
}

.version {
  font-size: 10px;
}

/* 响应式设计 */
@media (max-width: 450px) {
  .login-form {
    width: 90%;
    padding: 20px;
  }

  .login-button,
  .reset-button {
    width: 100px;
    padding: 10px 20px;
  }

  .button-group {
    flex-direction: column;
    gap: 10px;
  }
}
</style>

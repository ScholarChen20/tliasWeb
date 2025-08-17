<script setup>
import { ref } from 'vue'
import { loginApi} from '@/api/login'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const loginForm = ref({username:'', password:''})
const router = useRouter();

// 登录
const login = async () => {
  const result = await loginApi(loginForm.value);
  if(result.code){ //成功
    //1. 提示信息
    ElMessage.success('登录成功');
    //2. 存储当前登录员工信息
    localStorage.setItem('loginUser',JSON.stringify(result.data));
    //3. 跳转页面 - 首页
    router.push('/index');
  }else { //失败
    ElMessage.error(result.msg);
  }
}

// 重置
const clear = () => {
  loginForm.value = {username:'', password:''};
}
</script>

<template>
  <div id="container">
    <div class="login-form">
      <el-form label-width="80px">
        <p class="title">智能后台管理系统</p>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button class="button" type="primary" @click="login">登 录</el-button>
          <el-button class="button" type="info" @click="clear">重 置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
/* 让背景填充整个页面 */
#container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('../../assets/bg1.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0; /* 移除原来的padding */
}

.login-form {
  max-width: 400px;
  padding: 30px;
  margin: 0 auto;
  border-radius: 10px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.3);
  /* 设置半透明背景 */
  background-color: rgba(255, 255, 255, 0.8); /* 0.8是透明度，可调整 */
  backdrop-filter: blur(5px); /* 添加毛玻璃效果，可选 */
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.title {
  font-size: 30px;
  font-family: '楷体';
  text-align: center;
  margin-bottom: 30px;
  font-weight: bold;
  color: #333; /* 确保文字在透明背景下可见 */
}

.button {
  margin-top: 30px;
  width: 120px;
}

/* 调整表单元素的透明度 */
.el-form-item__label {
  color: #333 !important; /* 确保标签文字可见 */
}
.el-input__inner {
  background-color: rgba(255, 255, 255, 0.7) !important;
}
</style>
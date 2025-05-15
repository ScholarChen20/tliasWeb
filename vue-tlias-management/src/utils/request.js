import axios from 'axios';
import { ElMessage } from 'element-plus';
import router from '../router';

//创建axios实例对象
const request = axios.create({
  baseURL: '/api', //请求的基础路径
  timeout: 600000  // 请求超时时间
})

//axios的请求 request 拦截器 - 获取localStorage中的token, 在请求头中增加token请求头
request.interceptors.request.use( //请求拦截器
  (config) => { //成功回调
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));  //获取localStorage中的登录用户信息
    if(loginUser && loginUser.token){ //判断是否登录且有token
      config.headers.token = loginUser.token; //将token设置到请求头中
    }
    return config;  //返回配置
  },
  (error) => { //失败回调
    return Promise.reject(error)  //返回错误信息
  }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    if(error.response.status == 401){ //全等
      //提示信息
      ElMessage.error('登录超时，请重新登录');
      //跳转到登录页面
      router.push('/login');
    }else {
      ElMessage.error('接口访问异常');
    }
    return Promise.reject(error)  //返回错误信息
  }
)

export default request
Page({
  data: {
    username: '',
    password: '',
  },

  // 账号密码登录
  handleAccountLogin() {
    const { username, password } = this.data;
    
    // 表单验证
    if (!username || !password) {
      wx.showToast({
        title: '请输入用户名和密码',
        icon: 'none'
      });
      return;
    }

    // 显示加载中
    wx.showLoading({
      title: '登录中...',
      mask: true
    });

    // 调用登录接口
    wx.request({
      url: 'http://localhost:8080/userLogin',
      method: 'POST',
      data: {
        username: username,
        password: password
      },
      success: (res) => {
        if (res.data.code === 1) {
          // 登录成功
          const userInfo = res.data.data;
          
          // 保存用户信息和token
          wx.setStorageSync('userInfo', userInfo);
          wx.setStorageSync('token', userInfo.token); // 添加 Bearer 前缀
          
          // 更新全局登录状态
          getApp().globalData.userInfo = userInfo;
          getApp().globalData.isLogin = true;

          wx.showToast({
            title: '登录成功',
            icon: 'success'
          });

          // 延迟跳转到首页
          setTimeout(() => {
            wx.switchTab({
              url: '/pages/home/home'
            });
          }, 1500);
        } else {
          // 登录失败
          wx.showToast({
            title: res.data.msg || '登录失败',
            icon: 'none'
          });
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        });
      },
      complete: () => {
        wx.hideLoading();
      }
    });
  },

  // 微信登录按钮点击事件（直接调用 handleWechatLogin）
  onTapWechatLogin() {
    this.handleWechatLogin();
  },

  // 修改后的微信登录逻辑（仅用 code）
  handleWechatLogin() {
    wx.showLoading({ title: '准备登录...', mask: true });
    
    // 1. 获取微信 code
    wx.login({
      success: (loginRes) => {
        if (!loginRes.code) {
          wx.hideLoading();
          return this.showError('获取登录凭证失败');
        }
        console.log('微信 code:', loginRes.code); // 确认 code 有值
        wx.request({
          url: 'http://localhost:8080/wechatLogin',
          method: 'POST',
          data: `code=${loginRes.code}`, 
          header: { 'Content-Type': 'application/x-www-form-urlencoded' }, // 关键！明确指定 JSON 格式
          success: (res) => {
            if (res.data?.code === 1) {
              this.handleLoginSuccess(res.data.data);
            } else {
              this.showError(res.data?.msg || '登录失败');
            }
          },
          fail: (err) => {
            console.error('请求失败:', err); // 打印详细错误
            this.showError('网络异常，请重试');
          },
          complete: () => wx.hideLoading()
        });
      },
      fail: (err) => {
        wx.hideLoading();
        this.showError('微信登录服务异常');
      }
    });
  },

  // // 微信登录请求（保持不变）
  // requestWechatLogin(code) {
    
  // },

  // 登录成功处理（保持不变）
  handleLoginSuccess(userData) {
    if (!userData || !userData.token) {
      return this.showError('用户数据异常');
    }

    // 存储关键数据
    wx.setStorageSync('userInfo', {
      id: userData.id,
      openid: userData.openid,
      token: userData.token
    });
    
    wx.setStorageSync('token', userData.token);
    
    // 更新全局状态
    const app = getApp();
    app.globalData.userInfo = userData;
    app.globalData.isLogin = true;

    // 显示成功提示
    wx.showToast({
      title: '登录成功',
      icon: 'success',
      duration: 1500,
      success: () => {
        // 跳转到首页
        setTimeout(() => {
          wx.switchTab({ url: '/pages/home/home' });
        }, 1500);
      }
    });
  },

  // 错误处理（保持不变）
  showError(msg) {
    wx.hideLoading();
    wx.showToast({
      title: msg,
      icon: 'none',
      duration: 3000
    });
  }
}); 
// pages/mine/mine.js
Page({
  data: {
    isLogin: false,
    userInfo: null,
    statusBarHeight: 0
  },

  onLoad() {
    // 获取状态栏高度
    const systemInfo = wx.getSystemInfoSync();
    this.setData({
      statusBarHeight: systemInfo.statusBarHeight
    });
    
    // 检查登录状态
    this.checkLoginStatus();
  },

  onShow() {
    // 每次显示页面时检查登录状态
    this.checkLoginStatus();
  },

  // 检查登录状态
  checkLoginStatus() {
    const userInfo = wx.getStorageSync('userInfo');
    const token = wx.getStorageSync('token');
    
    if (userInfo && token) {
      this.setData({
        isLogin: true,
        // userInfo: userInfo
      });
      this.fetchUserInfo(userInfo.id);
    } else {
      this.setData({
        isLogin: false,
        userInfo: null
      });
    }
  },

  // 跳转到登录页面
  login() {
    wx.navigateTo({
      url: '/pages/login/login'
    });
  },
// 获取用户信息
  fetchUserInfo(userId) {
    wx.request({
      url: 'http://localhost:8080/users/' + userId,
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        if (res.data.code === 1) {
          const userInfo = res.data.data;
          this.setData({ userInfo });
          // 更新存储的用户信息
          wx.setStorageSync('userInfo', userInfo);
        } else {
          wx.showToast({
            title: res.data.msg || '获取信息失败',
            icon: 'none'
          });
          // 如果token失效，清除登录状态
          if (res.data.code === 401) {
            this.setData({
              isLogin: false,
              userInfo: null
            });
            wx.removeStorageSync('userInfo');
            wx.removeStorageSync('token');
          }
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        });
      }
    });
  },
  // 退出登录
  loginOut() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          // 清除存储的用户信息和token
          wx.removeStorageSync('userInfo');
          wx.removeStorageSync('token');
          this.setData({
            isLogin: false,
            userInfo: null
          });
          wx.showToast({ title: '已退出登录' });
          
          // 退出登录后跳转到登录页面
          setTimeout(() => {
            this.login();
          }, 1500);
        }
      }
    });
  },

  // 编辑个人信息
  editInfo() {
    wx.navigateTo({
      url: '/pages/mine-profile/mine-profile'
    });
  },

  // 页面导航
  navigateTo(e) {
    // 检查是否登录
    if (!this.data.isLogin) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      setTimeout(() => {
        this.login();
      }, 1500);
      return;
    }

    const url = e.currentTarget.dataset.url;
    wx.navigateTo({ url });
  }
});
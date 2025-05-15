// pages/knowledge-detail/knowledge-detail.js
Page({
  data: {
    article: null,
    navHeight: 0
  },

  onLoad(options) {
    const id = options.id;
    // 获取文章详情
    this.getArticleDetail(id);
    
    // 获取系统信息设置导航栏高度
    const systemInfo = wx.getSystemInfoSync();
    const navHeight = systemInfo.statusBarHeight + 44;
    this.setData({ navHeight });
    
    // 设置自定义导航栏高度变量
    wx.getApp().globalData.navHeight = navHeight;
  },

  // 获取文章详情
  getArticleDetail(id) {
    wx.showLoading({
      title: '加载中...'
    });
    
    wx.request({
      url: `http://localhost:8080/knowledge/${id}`,
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        console.log('文章详情：', res.data);
        if (res.data.code === 1 && res.data.data) {
          this.setData({
            article: res.data.data
          });
        } else {
          wx.showToast({
            title: res.data.msg || '加载失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('请求失败：', err);
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      },
      complete: () => {
        wx.hideLoading();
      }
    });
  },

  // 处理返回事件
  handleBack() {
    // 判断是否可以返回
    const pages = getCurrentPages();
    if (pages.length > 1) {
      wx.navigateBack({
        delta: 1
      });
    } else {
      // 如果没有上一页，则跳转到知识列表页
      wx.redirectTo({
        url: '/pages/knowledge/knowledge'
      });
    }
  },

  // 分享功能
  onShareAppMessage() {
    const { article } = this.data;
    return {
      title: article.title,
      path: `/pages/knowledge-detail/knowledge-detail?id=${article.id}`
    };
  }
})
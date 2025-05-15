// pages/home/home.js
Page({
  data: {
    bannerList: [
      { imageUrl: '/asserts/banner/banner1.png' },
      { imageUrl: '/asserts/banner/banner2.png' },
      { imageUrl: '/asserts/banner/banner3.jpg' }
    ],
    lastRecord: {
      systolic: '--',  // 默认显示为--
      diastolic: '--',
      measureTime: '',
      heartRate: '--'
    },
    target: {
      systolic: 130,
      diastolic: 80
    }
  },

  onLoad() {
    this.fetchLastRecord();
    this.fetchTarget();
  },

  // 获取最近一次血压记录
  fetchLastRecord() {
    const userInfo = wx.getStorageSync('userInfo');
    if (!userInfo || !userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      return;
    }

    wx.request({
      url: `http://localhost:8080/userHbpInfos/latest/${userInfo.id}`,
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        console.log('获取到的最新记录：', res.data); // 添加日志
        if (res.data.code === 1 && res.data.data) {
          // 直接使用返回的单条记录
          const latestRecord = res.data.data;
          
          this.setData({
            lastRecord: {
              systolic: latestRecord.sbp || '--',
              diastolic: latestRecord.dbp || '--',
              heartRate: latestRecord.heart || '--',
              measureTime: this.formatDateTime(latestRecord.write_time) // 注意这里使用write_time
            }
          });
          console.log('设置后的lastRecord：', this.data.lastRecord); // 添加日志
        }
      },
      fail: (error) => {
        console.error('获取最近血压记录失败：', error);
      }
    });
  },

  // 格式化日期时间
  formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return '';
    const dt = new Date(dateTimeStr);
    const year = dt.getFullYear();
    const month = String(dt.getMonth() + 1).padStart(2, '0');
    const day = String(dt.getDate()).padStart(2, '0');
    const hours = String(dt.getHours()).padStart(2, '0');
    const minutes = String(dt.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  },

  // 获取目标值
  fetchTarget() {
    // TODO: 从后端获取医生设置的目标值
  },

  onShow() {
    this.fetchLastRecord(); // 每次显示页面时重新获取
  },

  // 页面导航
  navigateTo(e) {
    const url = e.currentTarget.dataset.url;
    wx.navigateTo({ url });
  }
})
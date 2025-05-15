// pages/bp-input/bp-input.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    date: '',
    time: '',
    systolic: '',
    diastolic: '',
    heartRate: '',
    arrhythmia: '正常',
    measureType: '家庭血压'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    // 设置默认时间为当前时间
    const now = new Date()
    const year = now.getFullYear()
    const month = String(now.getMonth() + 1).padStart(2, '0')
    const day = String(now.getDate()).padStart(2, '0')
    const hours = String(now.getHours()).padStart(2, '0')
    const minutes = String(now.getMinutes()).padStart(2, '0')
    
    this.setData({
      date: `${year}-${month}-${day}`,
      time: `${hours}:${minutes}`
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },

  // 日期选择器事件
  bindDateChange(e) {
    this.setData({
      date: e.detail.value
    })
  },

  // 输入事件处理函数
  inputSystolic(e) {
    this.setData({
      systolic: e.detail.value
    })
  },

  inputDiastolic(e) {
    this.setData({
      diastolic: e.detail.value
    })
  },

  inputHeartRate(e) {
    this.setData({
      heartRate: e.detail.value
    })
  },

  // 房颤选择
  selectArrhythmia(e) {
    this.setData({
      arrhythmia: e.currentTarget.dataset.value
    })
  },

  // 测量方式选择
  selectMeasureType(e) {
    this.setData({
      measureType: e.currentTarget.dataset.value
    })
  },

  // 保存记录
  saveRecord() {
    const { date, time, systolic, diastolic, heartRate, arrhythmia, measureType } = this.data
    
    // 验证输入
    if (!systolic || !diastolic || !heartRate) {
      wx.showToast({
        title: '请填写完整信息',
        icon: 'none'
      })
      return
    }

    // 获取用户信息
    const userInfo = wx.getStorageSync('userInfo');
    if (!userInfo || !userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      return;
    }

    // 构建记录对象，按后端字段要求命名
    const record = {
      userid: userInfo.id,
      sbp: parseInt(systolic),        // 收缩压
      dbp: parseInt(diastolic),       // 舒张压
      heart: parseInt(heartRate),     // 心率
      writeTime: `${date}T${time}:00`,  // 测量时间，使用ISO格式
      writeType: measureType,         // 测量方式
      situation: arrhythmia          // 房颤情况
    }

    wx.showLoading({
      title: '保存中...',
      mask: true
    });

    // 调用后端API保存记录
    wx.request({
      url: 'http://localhost:8080/userHbpInfos',
      method: 'POST',
      header: {
        'token': wx.getStorageSync('token'),
        'Content-Type': 'application/json'
      },
      data: record,
      success: (res) => {
        if (res.data.code === 1) {
          wx.showToast({
            title: '保存成功',
            icon: 'success',
            duration: 2000,
            success: () => {
              setTimeout(() => {
                wx.navigateBack()
              }, 2000)
            }
          });
        } else {
          wx.showToast({
            title: res.data.msg || '保存失败',
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
  }
})
// pages/advice/advice.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    adviceList: [],
    activeAdviceId: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.loadAdviceList();
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

  // 加载医生建议列表
  loadAdviceList() {
    const userInfo = wx.getStorageSync('userInfo');
    if (!userInfo || !userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      return;
    }

    wx.showLoading({
      title: '加载中...',
      mask: true
    });

    wx.request({
      url: `http://localhost:8080/reserves/advice/${userInfo.id}`,
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        if (res.data.code === 1) {
          // 处理返回的数据
          const adviceList = res.data.data.map(item => ({
            id: item.id,
            doctorName: item.dname || '暂无医生信息',
            department: item.dept, // 可以根据实际情况调整
            date: this.formatDateTime(item.appointTime),
            reason: item.reason,
            dietAdvice: item.dietAdvise || '暂无饮食建议',
            exerciseAdvice: item.exerciseAdvise || '暂无运动建议',
            medicationAdvice: item.medicationAdvise || '暂无用药建议',
            status: item.status
          }));

          this.setData({
            adviceList: adviceList,
            activeAdviceId: adviceList.length > 0 ? adviceList[0].id : null
          });
        } else {
          wx.showToast({
            title: res.data.msg || '获取建议失败',
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

  // 格式化日期时间
  formatDateTime(dateTimeStr) {
    if (!dateTimeStr) return '';
    const dt = new Date(dateTimeStr);
    const year = dt.getFullYear();
    const month = String(dt.getMonth() + 1).padStart(2, '0');
    const day = String(dt.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  },

  // 切换展开的建议
  toggleAdvice(e) {
    const adviceId = e.currentTarget.dataset.id;
    this.setData({
      activeAdviceId: this.data.activeAdviceId === adviceId ? null : adviceId
    });
  },

  // 复制建议内容
  copyAdvice(e) {
    const { content } = e.currentTarget.dataset;
    if (!content || content === '暂无建议') {
      wx.showToast({
        title: '暂无建议内容',
        icon: 'none'
      });
      return;
    }

    wx.setClipboardData({
      data: content,
      success() {
        wx.showToast({
          title: '已复制到剪贴板',
          icon: 'success'
        });
      }
    });
  }
})
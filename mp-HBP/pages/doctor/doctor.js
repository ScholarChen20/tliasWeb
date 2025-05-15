// pages/doctor/doctor.js
Page({
  data: {
    doctors: [],
    showBookingModal: false,
    selectedDoctor: null,
    bookingDate: '',
    bookingTime: '',
    bookingContent: '',
    minDate: '',
    maxDate: ''
  },

  // 添加 formatDate 函数
  formatDate(date) {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  },

  onLoad(options) {
    // 设置日期范围
    const today = new Date();
    const threeMonthsLater = new Date();
    threeMonthsLater.setMonth(threeMonthsLater.getMonth() + 3);

    this.setData({
      minDate: this.formatDate(today),
      maxDate: this.formatDate(threeMonthsLater)
    });

    // 加载医生列表
    this.loadDoctorList();
  },

  // 加载医生列表
  loadDoctorList() {
    wx.showLoading({
      title: '加载中...',
      mask: true
    });

    wx.request({
      url: 'http://localhost:8080/doctors/list',
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        if (res.data.code === 1) {
          console.log('医生列表数据：', res.data.data); // 添加日志查看数据
          this.setData({
            doctors: res.data.data
          });
        } else {
          wx.showToast({
            title: res.data.msg || '获取医生列表失败',
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

  // 显示预约弹窗
  showBooking(e) {
    const doctor = e.currentTarget.dataset.doctor;
    this.setData({
      showBookingModal: true,
      selectedDoctor: doctor,
      bookingDate: '',
      bookingTime: '',
      bookingContent: ''
    });
  },

  // 隐藏预约弹窗
  hideBooking() {
    this.setData({
      showBookingModal: false,
      selectedDoctor: null
    });
  },

  // 日期选择处理
  onDateChange(e) {
    this.setData({
      bookingDate: e.detail.value
    });
  },

  // 时间选择处理
  onTimeChange(e) {
    this.setData({
      bookingTime: e.detail.value
    });
  },

  // 预约内容输入处理
  onContentInput(e) {
    this.setData({
      bookingContent: e.detail.value
    });
  },

  // 提交预约
  submitBooking() {
    const { selectedDoctor, bookingDate, bookingTime, bookingContent } = this.data;
    
    if (!selectedDoctor || !selectedDoctor.id) {
      wx.showToast({
        title: '医生信息无效',
        icon: 'none'
      });
      return;
    }
    
    if (!bookingDate) {
      wx.showToast({
        title: '请选择预约日期',
        icon: 'none'
      });
      return;
    }

    if (!bookingTime) {
      wx.showToast({
        title: '请选择预约时间',
        icon: 'none'
      });
      return;
    }

    if (!bookingContent) {
      wx.showToast({
        title: '请填写预约内容',
        icon: 'none'
      });
      return;
    }

    const userInfo = wx.getStorageSync('userInfo');
    if (!userInfo || !userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      return;
    }

    wx.showLoading({
      title: '提交中...',
      mask: true
    });

    // 组合日期和时间
    const appointTime = `${bookingDate}T${bookingTime}:00`;
      // 构造请求数据
    const requestData = {
      did: selectedDoctor.id,  // 修改字段名为 dId  4
      userId: userInfo.id,   //3
      appointTime: appointTime, //
      reason: bookingContent
    };
    // 发送预约请求
    console.log('提交的预约数据：', requestData); // 添加日志查看提交的数据
    wx.request({
      url: 'http://localhost:8080/reserves',
      method: 'POST',
      header: {
        'token': wx.getStorageSync('token'),
        'Content-Type': 'application/json'
      },
      data: requestData,
      success: (res) => {
        if (res.data.code === 1) {
          wx.showToast({
            title: '预约成功',
            icon: 'success'
          });
          this.hideBooking();
        } else {
          wx.showToast({
            title: res.data.msg || '预约失败',
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
});
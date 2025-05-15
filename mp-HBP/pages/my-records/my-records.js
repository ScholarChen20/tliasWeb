// pages/my-records/my-records.js
Page({
  data: {
    records: [],
    sortOrder: 'desc' // 默认降序（最新的在前）
  },

  onLoad() {
    this.loadAppointmentRecords();
  },

  // 加载预约记录
  loadAppointmentRecords() {
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
      url: 'http://localhost:8080/reserves/user/' + userInfo.id,
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        if (res.data.code === 1) {
          // 处理返回的数据
          const records = res.data.data.map(item => ({
            id: item.id,
            dname: item.dname,
            appointTime: item.appointTime.split('T').join(' '), // 转换时间格式
            reason: item.reason,
            status: this.convertStatus(item.status)
          }));
          
          this.setData({ records }, () => {
            this.sortRecords();
          });
        } else {
          wx.showToast({
            title: res.data.msg || '获取记录失败',
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

  // 转换状态为英文标识
  convertStatus(status) {
    const statusMap = {
      '待就诊': 'upcoming',
      '已完成': 'completed',
      '已取消': 'cancelled'
    };
    return statusMap[status] || status;
  },

  // 获取状态文本
  getStatusText(status) {
    const statusMap = {
      'upcoming': '待就诊',
      'completed': '已完成',
      'cancelled': '已取消'
    };
    return statusMap[status] || status;
  },

  // 删除预约记录
  deleteRecord(e) {
    const recordId = e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确定要取消这条预约吗？',
      success: (res) => {
        if (res.confirm) {
          wx.showLoading({
            title: '取消中...',
            mask: true
          });

          // TODO: 调用取消预约的接口
          wx.request({
            url: 'http://localhost:8080/reserves/' + recordId,
            method: 'DELETE',
            header: {
              'token': wx.getStorageSync('token')
            },
            success: (res) => {
              if (res.data.code === 1) {
                wx.showToast({
                  title: '取消成功',
                  icon: 'success'
                });
                // 重新加载预约记录
                this.loadAppointmentRecords();
              } else {
                wx.showToast({
                  title: res.data.msg || '取消失败',
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
      }
    });
  },

  // 切换排序方式
  toggleSort() {
    const newOrder = this.data.sortOrder === 'desc' ? 'asc' : 'desc';
    this.setData({ sortOrder: newOrder }, () => {
      this.sortRecords();
    });
  },

  // 排序记录
  sortRecords() {
    const sortedRecords = [...this.data.records].sort((a, b) => {
      const timeA = new Date(a.appointmentTime).getTime();
      const timeB = new Date(b.appointmentTime).getTime();
      return this.data.sortOrder === 'desc' ? timeB - timeA : timeA - timeB;
    });
    this.setData({ records: sortedRecords });
  }
});
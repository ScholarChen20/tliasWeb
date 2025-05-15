// pages/mine/profile/profile.js
Page({
  data: {
    userInfo: {
      id: '',
      userId: '',
      password: '',
      image: '',
      username: '',
      name: '',
      sex: '',
      birthDate: '',
      phone: ''
    }
  },

  onLoad() {
    // 从本地存储获取用户ID
    const userInfo = wx.getStorageSync('userInfo');
    if (userInfo && userInfo.id) {
      this.fetchUserInfo(userInfo.id);
    }
  },

  // 获取用户信息
  fetchUserInfo(id) {
    wx.showLoading({
      title: '加载中...',
      mask: true
    });

    // 调用后端接口获取用户信息
    wx.request({
      url: 'http://localhost:8080/users/' + id,
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token') // 使用 Authorization 头
      },
      success: (res) => {
        if (res.data.code === 1) {
          // 直接使用返回的数据，不需要处理日期格式
          this.setData({ 
            userInfo: res.data.data 
          });
        } else {
          wx.showToast({
            title: res.data.msg || '获取信息失败',
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

  // 选择头像
  chooseAvatar() {
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        // TODO: 实现图片上传到服务器的功能
        const userInfo = this.data.userInfo;
        userInfo.image = res.tempFilePaths[0];
        this.setData({ userInfo });
      }
    });
  },

  // 输入框变化处理
  handleInput(e) {
    const { field } = e.currentTarget.dataset;
    const { value } = e.detail;
    this.setData({
      ['userInfo.' + field]: value
    });
  },

  // 性别选择
  radioChange(e) {
    this.setData({
      'userInfo.sex': e.detail.value === '1' ? '男' : '女'
    });
  },

  // 日期选择
  bindDateChange(e) {
    this.setData({
      'userInfo.birthDate': e.detail.value
    });
  },

  // 表单验证
  validateForm() {
    const { username, name, phone, userId } = this.data.userInfo;
    
    if (!username) {
      wx.showToast({
        title: '请输入用户名',
        icon: 'none'
      });
      return false;
    }
    
    if (!name) {
      wx.showToast({
        title: '请输入真实姓名',
        icon: 'none'
      });
      return false;
    }
    
    if (!phone) {
      wx.showToast({
        title: '请输入手机号码',
        icon: 'none'
      });
      return false;
    }
    
    if (!/^1\d{10}$/.test(phone)) {
      wx.showToast({
        title: '手机号格式不正确',
        icon: 'none'
      });
      return false;
    }
    
    if (!userId) {
      wx.showToast({
        title: '请输入身份证号',
        icon: 'none'
      });
      return false;
    }
    
    if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(userId)) {
      wx.showToast({
        title: '身份证号格式不正确',
        icon: 'none'
      });
      return false;
    }
    
    return true;
  },

  // 保存个人信息
  saveProfile() {
    if (!this.validateForm()) return;

    wx.showLoading({
      title: '保存中...',
      mask: true
    });

    // 调用后端接口更新用户信息
    wx.request({
      url: 'http://localhost:8080/users',
      method: 'PUT',
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token') // 使用 Authorization 头
      },
      data: this.data.userInfo,
      success: (res) => {
        if (res.data.code === 1) {
          wx.showToast({
            title: '保存成功',
            icon: 'success'
          });
          
          // 更新本地存储的用户信息
          wx.setStorageSync('userInfo', this.data.userInfo);
          
          // 延迟返回上一页
          setTimeout(() => {
            wx.navigateBack();
          }, 1500);
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
});
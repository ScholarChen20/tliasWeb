// components/custom-nav/custom-nav.js
Component({
  properties: {
    title: {
      type: String,
      value: ''
    },
    showBack: {
      type: Boolean,
      value: true
    }
  },

  data: {
    statusBarHeight: 0
  },

  lifetimes: {
    attached() {
      // 获取状态栏高度
      const systemInfo = wx.getSystemInfoSync();
      this.setData({
        statusBarHeight: systemInfo.statusBarHeight
      });
    }
  },

  methods: {
    handleBack() {
      const pages = getCurrentPages();
      if (pages.length > 1) {
        wx.navigateBack({ delta: 1 });
      } else {
        wx.switchTab({
          url: '/pages/home/home'
        });
      }
    }
  }
});
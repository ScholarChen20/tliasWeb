// pages/knowledge/knowledge.js
Page({
  data: {
    articles: [],
    page: 1,
    pageSize: 10,
    loading: false,
    hasMore: true,
    total: 0
  },

  onLoad() {
    this.loadArticles()
  },

  // 加载文章列表
  loadArticles() {
    if (this.data.loading || !this.data.hasMore) return
    
    this.setData({ loading: true })
    wx.request({
      url: 'http://localhost:8080/knowledge',
      method: 'GET',
      data: {
        page: this.data.page,
        pageSize: this.data.pageSize
      },
      header: {
        'content-type': 'application/json',
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        console.log('API Response:', res.data)
        if (res.data.code === 1 && res.data.data) {
          const { rows = [], total = 0 } = res.data.data
          if (Array.isArray(rows)) {
            this.setData({
              articles: this.data.page === 1 ? rows : this.data.articles.concat(rows),
              total,
              hasMore: this.data.articles.length < total,
              page: this.data.page + 1
            })
          } else {
            console.error('API返回的rows不是数组格式:', rows)
            wx.showToast({
              title: '数据格式错误',
              icon: 'none'
            })
          }
        } else {
          wx.showToast({
            title: res.data.msg || '加载失败',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        console.error('请求失败：', err)
        if (err.statusCode === 401) {
          wx.showToast({
            title: '请先登录',
            icon: 'none'
          })
        } else {
          wx.showToast({
            title: '网络错误',
            icon: 'none'
          })
        }
      },
      complete: () => {
        this.setData({ loading: false })
        wx.stopPullDownRefresh()
      }
    })
  },

  // 跳转到文章详情页
  goToDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/knowledge-detail/knowledge-detail?id=${id}`,
      fail: (err) => {
        console.error('页面跳转失败：', err)
        wx.showToast({
          title: '页面跳转失败',
          icon: 'none'
        })
      }
    })
  },

  // 下拉刷新
  onPullDownRefresh() {
    this.setData({
      articles: [],
      page: 1,
      hasMore: true
    }, () => {
      this.loadArticles()
    })
  },

  // 上拉加载更多
  onReachBottom() {
    if (this.data.hasMore) {
      this.loadArticles()
    }
  }
})
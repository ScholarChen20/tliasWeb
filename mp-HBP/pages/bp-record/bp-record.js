// pages/bp-record/bp-record.js
import * as echarts from '../../ec-canvas/echarts';

Page({
  data: {
    viewType: 'text',
    records: [], // 血压记录列表
    ec: {
      lazyLoad: true // 改为延迟加载模式
    }
  },

  onLoad(options) {
    this.loadRecords();
  },

  // 加载血压记录
  loadRecords() {
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
      url: 'http://localhost:8080/userHbpInfos/user/' + userInfo.id,
      method: 'GET',
      header: {
        'token': wx.getStorageSync('token')
      },
      success: (res) => {
        if (res.data.code === 1) {
          // 处理返回的数据
          let records = [];
          
          // 检查数据是否为数组
          if (Array.isArray(res.data.data)) {
            records = res.data.data.map(item => ({
              id: item.id,
              measureTime: this.formatDateTime(item.writeTime),
              systolic: item.sbp,
              diastolic: item.dbp,
              heartRate: item.heart,
              measureType: item.writeType,
              arrhythmia: item.situation,
              bpLevel: this.getBPLevel(item.sbp, item.dbp)
            }));
          } else if (res.data.data && typeof res.data.data === 'object') {
            // 如果是单个对象，转换为数组
            records = [this.transformRecord(res.data.data)];
          } else {
            // 如果数据为空或无效
            console.warn('获取到的记录数据格式异常:', res.data.data);
          }

          this.setData({
            records: records
          });

          // 如果是图表视图，初始化图表
          if (this.data.viewType === 'chart') {
            this.initChart();
          }
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

  // 转换单个记录
  transformRecord(item) {
    return {
      id: item.id,
      measureTime: this.formatDateTime(item.writeTime),
      systolic: item.sbp,
      diastolic: item.dbp,
      heartRate: item.heart,
      measureType: item.writeType,
      arrhythmia: item.situation,
      bpLevel: this.getBPLevel(item.sbp, item.dbp)
    };
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

  // 计算血压等级
  getBPLevel(sbp, dbp) {
    sbp = parseInt(sbp);
    dbp = parseInt(dbp);
    
    if (sbp < 120 && dbp < 80) return '正常';
    if (sbp < 130 && dbp < 80) return '正常高值';
    if (sbp < 140 && dbp < 90) return '正常高值';
    if (sbp < 160 || dbp < 100) return '1级高血压';
    if (sbp < 180 || dbp < 110) return '2级高血压';
    return '3级高血压';
  },

  // 初始化图表
  initChart() {
    const ecComponent = this.selectComponent('#bpChart');
    ecComponent.init((canvas, width, height, dpr) => {
      const chart = echarts.init(canvas, null, {
        width: width,
        height: height,
        devicePixelRatio: dpr
      });

      // 准备图表数据
      const records = this.data.records;
      const dates = records.map(r => r.measureTime.split(' ')[0]);
      const systolicData = records.map(r => r.systolic);
      const diastolicData = records.map(r => r.diastolic);
      const heartRateData = records.map(r => r.heartRate);

      const option = {
        title: {
          text: '血压趋势',
          left: 'center'
        },
        legend: {
          data: ['收缩压', '舒张压', '心率'],
          top: 30,
          left: 'center',
          z: 100
        },
        grid: {
          containLabel: true,
          top: 70,
          bottom: 20
        },
        tooltip: {
          show: true,
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: dates.reverse(),
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          axisLabel: {
            fontSize: 12
          }
        },
        yAxis: {
          x: 'center',
          type: 'value',
          splitLine: {
            lineStyle: {
              type: 'dashed'
            }
          },
          axisLine: {
            lineStyle: {
              color: '#999'
            }
          },
          axisLabel: {
            fontSize: 12
          }
        },
        series: [{
          name: '收缩压',
          type: 'line',
          smooth: true,
          data: systolicData.reverse(),
          itemStyle: {
            color: '#2d8cf0'
          }
        }, {
          name: '舒张压',
          type: 'line',
          smooth: true,
          data: diastolicData.reverse(),
          itemStyle: {
            color: '#ff9900'
          }
        }, {
          name: '心率',
          type: 'line',
          smooth: true,
          data: heartRateData.reverse(),
          itemStyle: {
            color: '#666666'
          }
        }]
      };

      chart.setOption(option);
      return chart;
    });
  },

  // 切换视图
  switchView(e) {
    const type = e.currentTarget.dataset.type;
    this.setData({
      viewType: type
    });
    
    if (type === 'chart') {
      this.initChart();
    }
  },

  // 显示详情
  showDetail(e) {
    const record = e.currentTarget.dataset.record;
    console.log('显示详情:', record);
    // TODO: 跳转到详情页或显示详情弹窗
  }
});

<script setup>
import { ref ,onMounted, watch } from 'vue'
import * as echarts from 'echarts';
import {
  empJobData,
  empGenderData
} from '@/api/report'
import { ElMessage, ElMessageBox } from 'element-plus'

onMounted(() => {
  getToken();
  getGenderData();
  getJobData();
  setTimeout(() => {
    updateJobChart();
    updateGenderChart();
  }, 500);
})

//获取token
const token = ref('')
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
}

//员工性别数据, 职位数据
const jobData = ref([])
const genderData = ref([])

empJobData().then(response => {
  jobData.value = response.data
})

//员工职位数据
const getJobData = async () => {
  const result = await empJobData();
  if(result.code){
    jobData.value = result.data;
  }else{
    ElMessage.error(result.msg);
  }
}
//获取员工性别数据
const getGenderData = async () => {  //async 异步函数
  const result = await empGenderData();
  if(result.code){
    genderData.value = result.data;
  }else{
    ElMessage.error(result.msg);
  }
}

//option
const Joboption = {
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'bar',
      itemStyle: {
        color: function(params) {
          const colorList = ['#d7514e', '#2f4554', '#61a0a8', '#d48265', '#749f83', '#ca8622', '#bda29a', '#6e7074', '#546570', '#c4ccd3', '#f05b72', '#ef5b9c', '#f47920', '#905a3d', '#fab27b', '#2a5caa', '#444693', '#726930', '#b2d235', '#6d8346', '#ac6767', '#1d953f', '#6950a1', '#918597', '#f6f5ec'];
          return colorList[Math.round(Math.random() * (colorList.length - 1))];
        }
      }
    }
  ]
};

const Genderoption = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
      ]
    }
  ]
}

const jobChart = ref()
const genderChart = ref()

let jobChartInstance = null
let genderChartInstance = null

// 更新职位统计图表
const updateJobChart = () => {
  if (!jobData.value || !jobData.value.jobList) return;
  Joboption.xAxis.data = jobData.value.jobList;
  Joboption.series[0].data = jobData.value.dataList;
  if (jobChart.value) {
    if (!jobChartInstance) {
      jobChartInstance = echarts.init(jobChart.value);
    }
    jobChartInstance.setOption(Joboption);
  }
}
// 更新性别统计图表
const updateGenderChart = () => {
  if (!genderData.value || !Array.isArray(genderData.value)) return;
  Genderoption.series[0].data = genderData.value;
  if (genderChart.value) {
    if (!genderChartInstance) {
      genderChartInstance = echarts.init(genderChart.value);
    }
    genderChartInstance.setOption(Genderoption);
  }
}

// 监听数据变化，更新图表
watch(jobData, updateJobChart)
watch(genderData, updateGenderChart)

</script>

<template>
  <div style="display: flex; gap: 24px;">
    <div style="flex: 1; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px #f0f1f2;">
      <h3 style="text-align:center;">员工职位统计</h3>
      <div ref="jobChart" style="width: 100%; height: 400px; padding-top: 24px;"></div>
    </div>
    <div style="flex: 1; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px #f0f1f2;">
      <h3 style="text-align:center;">员工性别统计</h3>
      <div ref="genderChart" style="width: 100%; height: 400px; panding-top: 24px;"></div>
    </div>
  </div>
</template>

<style scoped>

</style>
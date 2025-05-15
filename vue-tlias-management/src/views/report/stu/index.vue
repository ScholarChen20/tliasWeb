<script setup>
import { ref ,onMounted, watch } from 'vue'
import * as echarts from 'echarts';
import {
  studentCountData,
  studentDegreeData
} from '@/api/report'
import { ElMessage, ElMessageBox } from 'element-plus'

onMounted(() => {
  getToken();
  getClassData();
  getDegreeData();
  setTimeout(() => {
    updateclassChart();
    updatedegreeChart();
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
const classData = ref([])
const degreeData = ref([])

studentCountData().then(response => {
  classData.value = response.data
})

//员工职位数据
const getClassData = async () => {
  const result = await studentCountData();
  if(result.code){
    classData.value = result.data;
  }else{
    ElMessage.error(result.msg);
  }
}
//获取员工性别数据
const getDegreeData = async () => {  //async 异步函数
  const result = await studentDegreeData();
  if(result.code){
    degreeData.value = result.data;
  }else{
    ElMessage.error(result.msg);
  }
}

//option
const Classoption = {
  grid: {
    left: '10%',
    right: '1%',
    bottom: '10%',
    containLabel: true
  },
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

const Degreeoption = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: 'Degree',
      type: 'pie',
      radius: ['30%', '60%'],
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

const classChart = ref()
const degreeChart = ref()

let classChartInstance = null
let degreeChartInstance = null

// 更新职位统计图表
const updateclassChart = () => {
  if (!classData.value || !classData.value.clazzList) return;
  Classoption.xAxis.data = classData.value.clazzList;
  Classoption.series[0].data = classData.value.dataList;
  if (classChart.value) {
    if (!classChartInstance) {
      classChartInstance = echarts.init(classChart.value);
    }
    classChartInstance.setOption(Classoption);
  }
}
// 更新性别统计图表
const updatedegreeChart = () => {
  if (!degreeData.value || !Array.isArray(degreeData.value)) return;
  Degreeoption.series[0].data = degreeData.value;
  if (degreeChart.value) {
    if (!degreeChartInstance) {
      degreeChartInstance = echarts.init(degreeChart.value);
    }
    degreeChartInstance.setOption(Degreeoption);
  }
}

// 监听数据变化，更新图表
watch(classData, updateclassChart)
watch(degreeData, updatedegreeChart)

</script>

<template>
  <div style="display: flex; gap: 24px;">
    <div style="flex: 1; background: #fff; padding: 10px 24px; border-radius: 8px; box-shadow: 0 2px 8px #f0f1f2;">
      <h3 style="text-align:center;">班级人数统计</h3>
      <div ref="classChart" style="width: 120%; height: 320px; padding-top: 24px; padding-left: 24px;"></div>
    </div>
    <div style="flex: 1; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 8px #f0f1f2;">
      <h3 style="text-align:center;">学员学历统计</h3>
      <div ref="degreeChart" style="width: 100%; height: 300px; padding-top: 50px; padding-left: 24px;"></div>
    </div>
  </div>
</template>

<style scoped>

</style>
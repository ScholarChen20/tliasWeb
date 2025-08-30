<template>
  <h1>血压记录管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="用户">
        <el-select v-model="searchEmp.userId" placeholder="请选择">
          <el-option v-for="u in users" :key="u.id" :label="u.name" :value="u.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="记录时间">
        <el-date-picker
            v-model="searchEmp.date"
            type="daterange"
            range-separator="到"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div class="container">
    <el-button type="primary" @click="addRecord"> + 新增血压记录</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="bpList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="80" align="center"/>
      <el-table-column prop="userName" label="用户" width="120" align="center"/>
      <el-table-column prop="sbp" label="收缩压" width="120" align="center"/>
      <el-table-column prop="dbp" label="舒张压" width="120" align="center"/>
      <el-table-column prop="heart" label="心率" width="120" align="center"/>
      <el-table-column prop="writeType" label="测量方式" width="130" align="center"/>
      <el-table-column prop="situation" label="房颤" width="130" align="center"/>
      <el-table-column prop="writeTime" label="记录时间" width="200" align="center"/>
      <el-table-column prop="updateTime" label="最后操作时间" width="230" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="success" size="small" @click="showRecord(scope.row.userid)"><el-icon><SuccessFilled /></el-icon> 可视化显示</el-button>
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>
          <el-button type="danger" size="small" @click="delById(scope.row.id)"><el-icon><Delete /></el-icon> 删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 分页条 -->
  <div class="container">
    <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>

  <!-- 可视化图表 Dialog -->
  <el-dialog
      v-model="chartVisible"
      title=""
      width="800px"
      height="600px"
      :before-close="handleClose"
  >
    <div ref="chartRef" style="width: 100%; height: 450px; margin-top: 20px;"></div>
  </el-dialog>

  <!-- Dialog对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="bp" :rules="rules" ref="deptFormRef">
      <el-form-item label="用户" label-width="80px">
        <el-select v-model="bp.userid" placeholder="请选择用户" style="width: 100%;">
          <el-option v-for="u in users" :key="u.id" :label="u.name" :value="u.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收缩压" label-width="80px" prop="sbp">
        <el-input v-model="bp.sbp" />
      </el-form-item>
      <el-form-item label="舒张压" label-width="80px" prop="dbp">
        <el-input v-model="bp.dbp" />
      </el-form-item>
      <el-form-item label="心率" label-width="80px" prop="heart">
        <el-input v-model="bp.heart" />
      </el-form-item>
      <el-form-item label="测量方式" label-width="80px">
        <el-select v-model="bp.writeType" placeholder="请选择" style="width: 100%;">
          <el-option v-for="w in wType" :key="w.value" :label="w.name" :value="w.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="房颤" label-width="80px">
        <el-select v-model="bp.situation" placeholder="请选择" style="width: 100%;">
          <el-option v-for="s in situation" :key="s.value" :label="s.name" :value="s.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="记录时间" label-width="80px" prop="writeTime">
        <el-date-picker
            v-model="bp.writeTime"
            type="datetime"
            placeholder="选择记录时间"
            style="width: 100%;"
            :disabled="!!bp.writeTime"
            value-format="YYYY-MM-DD HH:mm:ss"
            default-time="12:00:00">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  queryAllUserApi,
  queryInfoApi,
  queryWeeklyApi
} from '@/api/bp-record';
import { ElMessage, ElMessageBox } from 'element-plus';
import { SuccessFilled, EditPen, Delete } from '@element-plus/icons-vue';
import dayjs from 'dayjs';
import * as echarts from 'echarts';

// 钩子函数
onMounted(() => {
  search();
  queryAllUsers();
});

// 查询列表
const bpList = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const background = ref(true);
const total = ref(0);

// 搜索表单
const searchEmp = ref({ userId: '', date: [], begin: '', end: '' });

watch(() => searchEmp.value.date, (newVal) => {
  if (newVal.length === 2) {
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  } else {
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
});

const search = async () => {
  const result = await queryPageApi(
    searchEmp.value.userId,
    searchEmp.value.begin,
    searchEmp.value.end,
    currentPage.value,
    pageSize.value
  );
  if (result.code) {
    bpList.value = result.data.rows;
    total.value = result.data.total;
  }
};

const clear = () => {
  searchEmp.value = { userId: '', date: [], begin: '', end: '' };
  search();
};

// 分页事件
const handleSizeChange = (val) => {
  pageSize.value = val;
  search();
};
const handleCurrentChange = (val) => {
  currentPage.value = val;
  search();
};

// 用户列表
const users = ref([]);
const queryAllUsers = async () => {
  const result = await queryAllUserApi();
  if (result.code) {
    users.value = result.data;
  }
};

// 对话框相关
const dialogFormVisible = ref(false);
const formTitle = ref('');
const bp = ref({
  name: '',
  userid: '',
  sbp: '',
  dbp: '',
  heart: '',
  writeType: '',
  situation: '',
  writeTime: ''
});
const deptFormRef = ref(null);

const addRecord = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增血压记录';
  bp.value = {
    name: '',
    userid: '',
    sbp: '',
    dbp: '',
    heart: '',
    writeType: '',
    situation: '',
    writeTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
  };
  if (deptFormRef.value) {
    deptFormRef.value.resetFields();
  }
};

const save = async () => {
  if (!deptFormRef.value) return;
  await deptFormRef.value.validate(async (valid) => {
    if (valid) {
      let result;
      if (bp.value.id) {
        result = await updateApi(bp.value);
      } else {
        result = await addApi(bp.value);
      }
      if (result.code) {
        ElMessage.success('操作成功');
        dialogFormVisible.value = false;
        search();
      } else {
        ElMessage.error(result.msg);
      }
    } else {
      ElMessage.error('表单校验不通过');
    }
  });
};

const edit = async (id) => {
  formTitle.value = '修改记录';
  if (deptFormRef.value) {
    deptFormRef.value.resetFields();
  }
  const result = await queryInfoApi(id);
  if (result.code) {
    dialogFormVisible.value = true;
    bp.value = result.data;
    bp.value.userid = result.data.userid;
  }
};

const delById = async (id) => {
  ElMessageBox.confirm('您确认删除该血压记录吗?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    const result = await deleteApi(id);
    if (result.code) {
      ElMessage.success('删除成功');
      search();
    } else {
      ElMessage.error(result.msg);
    }
  }).catch(() => {
    ElMessage.info('您已取消删除');
  });
};

// ==================== 可视化图表部分 ====================

const chartVisible = ref(false);
const chartRef = ref(null);
const chartInstance = ref(null);

// 显示图表
const showRecord = async (userId) => {
  chartVisible.value = true;
  await loadChart(userId);
};

// 加载图表数据
const loadChart = async (userId) => {
  const result = await queryWeeklyApi(userId);
  if (result.code && result.data) {
    const data = result.data;
    const dates = data.dates;
    const sbp = data.sbp;
    const dbp = data.dbp;
    const heart = data.heart;

    // 初始化图表
    chartInstance.value = echarts.init(chartRef.value);
        chartInstance.value.setOption({
      title: {
        text: '最近一周血压趋势',
        left: 'center',
        textStyle: {
          fontSize: 16,
          fontWeight: 'bold'
        }
      },
          tooltip: {
            trigger: 'axis',
          },
      legend: {
        data: ['收缩压', '舒张压', '心率'],
        top: '10%',
        left: 'center',
        textStyle: {
          fontSize: 12
        },
        itemWidth: 12,
        itemHeight: 8
      },
      grid: {
        left: '10%',
        right: '10%',
        top: '25%',  // 因为图例在上，所以 grid 的 top 要留出空间
        bottom: '20%'
      },
      xAxis: {
        type: 'category',
        data: dates,
        axisLabel: {
          rotate: 45,
          formatter: function(value) {
            return value.split(' ')[0];
          }
        },
        axisLine: {
          lineStyle: {
            color: '#999'
          }
        }
      },
      yAxis: {
        type: 'value',
        name: '数值',
        splitLine: {
          show: true,
          lineStyle: {
            color: '#eee'
          }
        },
        axisLine: {
          lineStyle: {
            color: '#999'
          }
        }
      },
      series: [
        {
          name: '收缩压',
          type: 'line',
          data: sbp,
          smooth: true,
          lineStyle: { color: '#ff7f50' },
          symbol: 'circle', // 可选：设置数据点形状
          symbolSize: 6     // 可选：设置数据点大小
        },
        {
          name: '舒张压',
          type: 'line',
          data: dbp,
          smooth: true,
          lineStyle: { color: '#0badef' },
          symbol: 'circle',
          symbolSize: 6
        },
        {
          name: '心率',
          type: 'line',
          data: heart,
          smooth: true,
          lineStyle: { color: '#619003' },
          symbol: 'circle',
          symbolSize: 6
        }
      ]
    })
  }
};

// 关闭图表时销毁实例
const handleClose = () => {
  if (chartInstance.value) {
    chartInstance.value.dispose();
    chartInstance.value = null;
  }
  chartVisible.value = false;
};
</script>

<style scoped>
.container {
  margin: 15px 0;
}
.dialog-footer {
  text-align: right;
}
</style>

<script setup>
import { ref, onMounted } from 'vue';
import { getLogs } from '@/api/log';
//钩子函数
onMounted(() => {
  search();
})

//查询
const LogList = ref([])
//查询员工列表
const search = async () => {
  const result = await getLogs( currentPage.value, pageSize.value);
  if(result.code){
    LogList.value = result.data.rows;
    total.value = result.data.total;
  }
}


//分页
const currentPage = ref(1); //页码
const pageSize = ref(10); //每页展示记录数
const background = ref(true); //背景色
const total = ref(0); //总记录数

//每页展示记录数变化
const handleSizeChange = (val) => {
  search();
}
//页码变化时触发
const handleCurrentChange = (val) => {
  search();
}


</script>

<template>
  <h1>日志信息管理</h1>
<!--  <div class="container">-->
<!--    <el-button type="primary" @click="addClazz"> + 新增班级</el-button>-->
<!--  </div>-->

  <!-- 表格 -->
  <div class="container">
    <el-table :data="LogList" border style="width: 100%">
      <el-table-column type="index" label="ID" width="100" align="center"/>
      <el-table-column prop="operateEmpName" label="操作人姓名" width="130" align="center"/>
      <el-table-column prop="className" label="操作类名" width="160" align="center"/>
      <el-table-column prop="methodName" label="操作方法名" width="180" align="center"/>
      <el-table-column prop="methodParams" label="操作方法参数" width="400" align="center"/>
      <el-table-column prop="returnValue" label="操作方法返回值" width="250" align="center"/>
      <el-table-column prop="costTime" label="操作耗时" width="90" align="center"/>
      <el-table-column prop="operateTime" label="操作时间" width="200" align="center"/>
<!--      <el-table-column label="操作" align="center">-->
<!--        <template #default="scope">-->
<!--          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>-->
<!--          <el-button type="danger" size="small" @click="delById(scope.row.id)"><el-icon><Delete /></el-icon> 删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
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

</template>

<style scoped>
.container {
  margin: 10px 0px;
}

.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>
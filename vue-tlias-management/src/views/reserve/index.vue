<script setup>
import { ref, onMounted,watch } from 'vue';
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  queryInfoApi,
} from '@/api/reserves';
import {queryAllUserApi} from "@/api/bp-record";
import {queryAllApi as queryAllDocApi} from "@/api/doctor"
import { ElMessage, ElMessageBox } from 'element-plus';
import {Delete, EditPen} from "@element-plus/icons-vue";

//钩子函数
onMounted(() => {
  search();
  queryAllUser();
  queryAllDoc();
})

//查询
const resList = ref([])
const userList = ref([])
const docList = ref([])
const stus = ref([{ name: '待就诊', value: "待就诊" }, { name: '已就诊', value: "已就诊" }])


const queryAllUser = async () => {
  const result = await queryAllUserApi();
  if(result.code){
    userList.value = result.data;
  }
}
const queryAllDoc = async () => {
  const result = await queryAllDocApi();
  if(result.code){
    docList.value = result.data;
  }
}


//搜索表单对象
//搜索表单对象
const searchEmp = ref({userId: '', dId: '', status: '', date: [], begin: '', end: ''})

watch(() => searchEmp.value.date, (newVal, oldVal) => {
  if(newVal.length == 2) {
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  }else {
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
})


const search = async () => {
  const result = await queryPageApi(searchEmp.value.userId,searchEmp.value.dId,searchEmp.value.status,
      searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value);
  // console.log(result);
  if(result.code){
    resList.value = result.data.rows;
    total.value = result.data.total;
  }
}

//清空
const clear = () => {
  searchEmp.value = {userId: '', dId: '', status: '', date: [], begin: '', end: ''};
  search();
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


// 控制弹窗
const dialogTitle = ref('')

//Dialog对话框
const dialogFormVisible = ref(false);
const formTitle = ref('');
const res = ref({userId: '', did: '',appointTime: '',reason: '',dietAdvise: '',exerciseAdvise: '',status: '',medicationAdvise: ''});//新增时默认值

//新增记录
const addRecord = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增医生记录';
  res.value = {userId: '', did: '',appointTime: '',reason: '',dietAdvise: '',exerciseAdvise: '',status: '',medicationAdvise: ''}; //新增时默认值

  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

}

//保存
const save = async () => {
  //表单校验
  if(!deptFormRef.value)   ElMessage.error('表单校验不通过');
  deptFormRef.value.validate(async (valid) => { //valid 表示是否校验通过: true 通过 / false  不通过
    if(valid){ //通过

      let result;
      console.log(res.value);
      if(res.value.id){ //修改
        result = await updateApi(res.value);
      }else{ //新增
        result = await addApi(res.value);
      }

      if(result.code){ //成功
        //提示信息
        ElMessage.success('操作成功');
        //关闭对话框
        dialogFormVisible.value = false;
        //查询
        search();
      }else{ //失败
        ElMessage.error(result.msg);
      }
    }else { //不通过
      ElMessage.error('表单校验不通过');
    }
  })
}


//表单校验
const rules = ref({
  reason: [
    { required: true, message: '请输入预约原因', trigger: 'blur' },
    { min: 2, max: 1000, message: '描述下病状', trigger: 'blur' }
  ],
})
const deptFormRef = ref();

//编辑
const edit = async (id) => {
  formTitle.value = '修改预约';
  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

  const result = await queryInfoApi(id);
  if(result.code){
    dialogFormVisible.value = true;
    dialogTitle.value = '修改预约';
    res.value = result.data;
    res.value.userId = result.data.userId;
    res.value.did = result.data.dId;
    console.log(result.data);
  }
}

//删除
const delById = async (id) => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该预约信息吗?','提示',
      { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
  ).then(async () => { //确认
    const result = await deleteApi(id);
    if(result.code){
      ElMessage.success('删除成功');
      search();
    }else{
      ElMessage.error(result.msg);
    }
  }).catch(() => { //取消
    ElMessage.info('您已取消删除');
  })
}
//记录勾选的id
const selectedIds = ref([]);
//复选框勾选发生变化时触发 - selection: 当前选中的记录 (数组)
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map( item => item.id);
}

//批量删除
const deleteByIds = () => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该信息吗?','提示',
      { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
  ).then(async () => { //确认
    if(selectedIds.value && selectedIds.value.length > 0){
      const result = await deleteApi(selectedIds.value);
      if(result.code){
        ElMessage.success('删除成功');
        search();
      }else{
        ElMessage.error(result.msg);
      }
    }else {
      ElMessage.info('您没有选择任何要删除的数据');
    }
  }).catch(() => { //取消
    ElMessage.info('您已取消删除');
  })
}

</script>

<template>
  <h1>预约管理</h1>
  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="用户">
        <el-select v-model="searchEmp.userId" placeholder="请选择">
          <el-option v-for="u in userList" :key="u.id" :label="u.name" :value="u.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="医生">
        <el-select v-model="searchEmp.dId" placeholder="请选择">
          <el-option v-for="d in docList" :key="d.id" :label="d.name" :value="d.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="就诊状态">
        <el-select v-model="searchEmp.status" placeholder="请选择">
          <el-option v-for="d in stus" :key="d.value" :label="d.name" :value="d.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="预约时间">
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
    <el-button type="primary" @click="addRecord"> + 新增预约</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="resList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="index" label="序号" width="100" align="center"/>
      <el-table-column prop="userName" label="用户" width="100" align="center"/>
      <el-table-column prop="dname" label="预约医生" width="100" align="center"/>
      <el-table-column prop="dept" label="科室" width="100" align="center"/>
      <el-table-column prop="reason" label="预约原因" width="100" align="center"/>
      <el-table-column prop="status" label="就诊状态" width="100" align="center"/>
      <el-table-column prop="appointTime" label="预约时间" width="200" align="center"/>
      <el-table-column prop="dietAdvise" label="饮食建议" width="200" align="center"/>
      <el-table-column prop="exerciseAdvise" label="运动建议" width="200" align="center"/>
      <el-table-column prop="medicationAdvise" label="用药建议" width="200" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
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

  <!-- Dialog对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle">
    <el-form :model="res" :rules="rules" ref="deptFormRef" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">

        <el-col :span="12">
          <el-form-item label="用户">
            <el-select v-model="res.userId" placeholder="请选择用户" style="width: 100%;">
              <el-option v-for="u in userList" :key="u.id" :label="d.name" :value="u.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="就诊状态">
            <el-select v-model="res.status" disabled placeholder="就诊状态">
              <el-option v-for="s in stus" :key="s.value" :label="s.name" :value="s.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="医生">
            <el-select v-model="res.did" placeholder="请选择医生" style="width: 100%;">
              <el-option v-for="d in docList" :key="d.id" :label="d.name" :value="d.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="所属科室">
            <el-select v-model="res.did" disabled placeholder="请选择科室" style="width: 100%;">
              <el-option v-for="d in docList" :key="d.id" :label="d.dept" :value="d.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="预约时间">
            <el-date-picker
              v-model=res.appointTime
              type="datetime"
              placeholder="选择日期时间"
              default-time="12:00:00">
          </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="预约原因">
            <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入预约原因"
                v-model="res.reason">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-form-item label="饮食建议">
          <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入饮食建议"
              v-model="res.dietAdvise">
          </el-input>
        </el-form-item>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="运动建议">
            <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入运动建议"
                v-model="res.exerciseAdvise">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 第六行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="用药建议">
            <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入用药建议"
                v-model="res.medicationAdvise">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>
.container {
  margin: 15px 0px;
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

</style>
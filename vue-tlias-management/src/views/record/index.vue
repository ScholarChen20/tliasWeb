<script setup>
import { ref, onMounted,watch } from 'vue';
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  queryAllUserApi,
  queryInfoApi,
} from '@/api/bp-record';
import { ElMessage, ElMessageBox } from 'element-plus';
import {SuccessFilled} from "@element-plus/icons-vue";

//钩子函数
onMounted(() => {
  search();
  queryAllUsers();

})

//查询
const bpList = ref([])


// //获取token
// const getToken = () => {
//   const loginUser = JSON.parse(localStorage.getItem('loginUser'));
//   if(loginUser && loginUser.token){
//     token.value = loginUser.token;
//   }
// }

const wType = ref([{ name: '家庭血压', value: "家庭血压" },{ name: '诊室血压', value: "诊室血压" }])
//性别列表数据
const situation = ref([{ name: '正常', value: "正常" }, {  name: '异常', value: "异常" }])
//用户列表数据
const users = ref([])

const queryAllUsers = async () => {
  const result = await queryAllUserApi();
  if(result.code){
    users.value = result.data;
  }
}


//搜索表单对象
const searchEmp = ref({userId: '', date: [], begin: '', end: ''})

watch(() => searchEmp.value.date, (newVal, oldVal) => {
  if(newVal.length === 2) {
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  }else {
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
})


const search = async () => {
  const result = await queryPageApi(searchEmp.value.userId,
      searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value);
  // console.log(result);
  if(result.code){
    bpList.value = result.data.rows;
    total.value = result.data.total;
  }
}

//清空
const clear = () => {
  searchEmp.value = {userId: '', date: [], begin: '', end: ''};
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

//Dialog对话框
const dialogFormVisible = ref(false);
const formTitle = ref('');
const bp = ref({name: '', userid: '',sbp: '',dbp: '',heart: '',writeType: '',situation: '',updateTime: ''});//新增时默认值

//新增记录
const addRecord = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增血压记录';
  bp.value = {name: '', userid: '',sbp: '',dbp: '',heart: '',writeType: '',situation: '',updateTime: ''}; //新增时默认值

  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

}

//保存部门
const save = async () => {
  //表单校验
  if(!deptFormRef.value) return;
  deptFormRef.value.validate(async (valid) => { //valid 表示是否校验通过: true 通过 / false  不通过
    if(valid){ //通过

      let result ;
      if(bp.value.id){ //修改
        result = await updateApi(bp.value);
      }else{ //新增
        result = await addApi(bp.value);
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
  sbp: [
    { required: true, message: '收缩压为90mmHg-139mmHg', trigger: 'blur' },
  ],
  dbp: [
    { required: true, message: '舒张压为60mmHg-89mmHg', trigger: 'blur' },
  ],
  heart: [
    { required: true, message: '  60～100次/分', trigger: 'blur' },
  ]

})
const deptFormRef = ref();

//编辑
const edit = async (id) => {
  formTitle.value = '修改记录';
  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

  const result = await queryInfoApi(id);
  if(result.code){
    dialogFormVisible.value = true;
    bp.value = result.data;
    bp.value.userid = result.data.userid
    console.log(result.data.userid);
  }
}

//删除
const delById = async (id) => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该血压记录吗?','提示',
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
</script>

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
      <el-table-column type="index" label="序号" width="100" align="center"/>
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
          <el-button type="success" size="small" @click="showRecord(scope.row.id)"><el-icon><success-filled /></el-icon> 可视化显示</el-button>
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
        <el-select v-model="bp.writeType" placeholder="请选择用户" style="width: 100%;">
          <el-option v-for="w in wType" :key="w.value" :label="w.name" :value="w.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="房颤" label-width="80px">
        <el-select v-model="bp.situation" placeholder="请选择用户" style="width: 100%;">
          <el-option v-for="s in situation" :key="s.value" :label="s.name" :value="s.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="更新时间" label-width="80px" prop="updateTime">
        <el-time-picker v-model="bp.updateTime" placeholder="选择时间">
        </el-time-picker>
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

<style scoped>
.container {
  margin: 15px 0px;
}
</style>
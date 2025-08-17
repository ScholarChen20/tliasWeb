<script setup>
import { ref, onMounted } from 'vue';
import {
  queryAllApi,
  addApi,
  queryInfoApi,
  updateApi,
  deleteApi,
  queryPageApi,
  queryEmp
} from '@/api/clazz';
import { ElMessage, ElMessageBox } from 'element-plus';

//钩子函数
onMounted(() => {
  search();
  getEmpList();
})

//查询
const deptList = ref([])
const search = async () => {
  const result = await queryAllApi();
  // console.log(result);
  if(result.code){
    deptList.value = result.data;
  }
}
//查询emp
const empList = ref([])
const getEmpList = async () => {
  const result = await queryEmp();
  if(result.code){
    empList.value = result.data;
  }
}

const subjects = ref([{ name: 'Java', value: 1 },{ name: '前端', value: 2 },{ name: '大数据', value: 3 }
  ,{ name: 'python', value: 4 },{ name: 'Go', value: 5 },{ name: '嵌入式', value: 6 }])

//Dialog对话框
const dialogFormVisible = ref(false);
const formTitle = ref('');
const dept = ref({name: '', room: '',beginDate: '',endDate: '',masterId: '',masterName: '',subject: '',updateTime: ''});//新增时默认值

//新增部门
const addClazz = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增班级';
  dept.value = {name: '', room: '',beginDate: '',endDate: '',masterId: '',subject: '',updateTime: ''}; //新增时默认值

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
      if(dept.value.id){ //修改
        result = await updateApi(dept.value);
      }else{ //新增
        result = await addApi(dept.value);
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
  name: [
    { required: true, message: '部门名称是必填项', trigger: 'blur' },
    { min: 2, max: 20, message: '部门名称的长度应该在2-20位', trigger: 'blur' }
  ]
})
const deptFormRef = ref();

//编辑
const edit = async (id) => {
  formTitle.value = '修改部门';
  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

  const result = await queryInfoApi(id);
  if(result.code){
    dialogFormVisible.value = true;
    dept.value = result.data;
    dept.value.masterId = result.data.masterId
  }
}

//删除
const delById = async (id) => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该部门吗?','提示',
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
  <h1>班级管理</h1>
  <div class="container">
    <el-button type="primary" @click="addClazz"> + 新增班级</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="deptList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center"/>
      <el-table-column prop="name" label="部门名称" width="100" align="center"/>
      <el-table-column prop="room" label="教室" width="100" align="center"/>
      <el-table-column prop="beginDate" label="开课时间" width="100" align="center"/>
      <el-table-column prop="endDate" label="结课时间" width="100" align="center"/>
      <el-table-column prop="room" label="教室" width="100" align="center"/>
      <el-table-column prop="masterName" label="班主任" width="100" align="center"/>
      <el-table-column prop="subject" label="学科" width="100"  align="center">
        <template #default="scope">
          <span v-if="scope.row.subject == 1">java</span>
          <span v-else-if="scope.row.subject == 2">前端</span>
          <span v-else-if="scope.row.subject == 3">大数据</span>
          <span v-else-if="scope.row.subject == 4">python</span>
          <span v-else-if="scope.row.subject == 5">Go</span>
          <span v-else-if="scope.row.subject == 6">嵌入式</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后操作时间" width="150" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>
          <el-button type="danger" size="small" @click="delById(scope.row.id)"><el-icon><Delete /></el-icon> 删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- Dialog对话框 -->
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="500">
    <el-form :model="dept" :rules="rules" ref="deptFormRef">
      <el-form-item label="班级名称" label-width="80px" prop="name">
        <el-input v-model="dept.name" />
      </el-form-item>
      <el-form-item label="教室" label-width="80px" prop="room">
        <el-input v-model="dept.room" />
      </el-form-item>
      <el-form-item label="开课时间" label-width="80px" prop="beginDate">
        <el-date-picker v-model="dept.beginDate" type="date" placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结课时间" label-width="80px" prop="endDate">
        <el-date-picker v-model="dept.endDate" type="date" placeholder="选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="班主任" label-width="80px" prop="masterName">
        <el-select v-model="dept.masterId" placeholder="请选择班主任">
          <el-option  v-for="emp in empList"
                      :key="emp.id"
                      :label="emp.name"
                      :value="emp.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学科" label-width="80px" prop="subject">
        <el-select v-model="dept.subject" placeholder="请选择学科">
          <el-option v-for="s in subjects" :key="s.value" :label="s.name" :value="s.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="更新时间" label-width="80px" prop="updateTime">
        <el-time-picker v-model="dept.updateTime" placeholder="选择时间">
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
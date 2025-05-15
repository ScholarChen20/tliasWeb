<script setup>
import { ref, watch, onMounted } from 'vue';
import { queryPageApi,queryInfoApi,deleteApi,addApi,updateApi,violationHandleApi } from '@/api/student';
import { queryAllApi as queryAllClazzApi } from '@/api/clazz';
import { ElMessage, ElMessageBox } from 'element-plus'
import {Monitor} from "@element-plus/icons-vue";

//token
const token = ref('');

//元数据
//职位列表数据
const degrees = ref([{ name: '初中', value: 1 },{ name: '高中', value: 2 },{ name: '大专', value: 3 },
  { name: '本科', value: 4 },{ name: '硕士', value: 5 },{ name: '博士', value: 6 }])
//性别列表数据
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 2 }])
const colleages = ref([{ name: '否', value: 0 }, { name: '是', value: 1 }])
//班级列表数据
const clazzsList = ref([])


//搜索表单对象
const searchEmp = ref({name: '', degree: '', clazzId: ''})

// watch(() => searchEmp.value.date, (newVal, oldVal) => {
//   if(newVal.length == 2){
//     searchEmp.value.begin = newVal[0];
//     searchEmp.value.end = newVal[1];
//   }else {
//     searchEmp.value.begin = '';
//     searchEmp.value.end = '';
//   }
// })

//钩子函数
onMounted(() => {
  search(); //查询员工列表数据
  queryAllClazz();//查询所有部门列表数据
  getToken(); //获取token
})

//获取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
}

//查询所有部门数据
const queryAllClazz = async () => {
  const result = await queryAllClazzApi();
  // console.log(result);
  if(result.code){
    clazzsList.value = result.data;
  }
}

//查询员工列表
const search = async () => {
  const result = await queryPageApi(searchEmp.value.name, searchEmp.value.degree,
      searchEmp.value.clazzId, currentPage.value, pageSize.value);
  if(result.code){
    // console.log(result);
    stuList.value = result.data.rows;
    total.value = result.data.total;
  }
}

//清空
const clear = () => {
  searchEmp.value = {name: '', degree: '', clazzId: ''};
  search();
}

//学员列表数据
const stuList = ref([])

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


//新增员工
const addEmp = () => {
  dialogVisible.value = true;
  dialogTitle.value = '新增学员';
  employee.value = {
    no: '',
    name: '',
    gender: '',
    phone: '',
    idCard: '',
    isCollege: '',
    address: '',
    degree: '',
    graduationDate: '',
    clazzId: '',
    violationCount: '',
    violationScore: '',
    // exprList: []
  }

  //重置表单的校验规则-提示信息
  if (empFormRef.value){
    empFormRef.value.resetFields();
  }
}

// 违纪分数
const student = ref({
  id: '',
  score: ''
})

//新增/修改表单
const employee = ref({
  no: '',
  name: '',
  gender: '',
  phone: '',
  idCard: '',
  isCollege: '',
  address: '',
  degree: '',
  graduationDate: '',
  clazzId: '',
  violationCount: '',
  violationScore: '',
  // exprList: []
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')
const violateDialogVisible = ref(false)
const violationDialogTitle = ref('违纪处理')

//保存员工
const save = async () => {
  //表单校验
  if(!empFormRef.value) return;
  empFormRef.value.validate(async (valid) => { //valid 表示是否校验通过: true 通过 / false  不通过
    if(valid){ //通过

      let result;
      if(employee.value.id){ //修改
        result = await updateApi(employee.value);
      }else { //新增
        result = await addApi(employee.value);
      }

      if(result.code) {//成功
        ElMessage.success('保存成功');
        dialogVisible.value = false;
        search();
      }else { //失败了
        ElMessage.error(result.msg);
      }
    }else { //不通过
      ElMessage.error('表单校验不通过');
    }
  })
}
//表单引用
const empFormRef = ref();

//表单校验规则
const rules = ref({
  no: [
    { required: true, message: '请输入序号', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    /**
     * 正则表达式: / ..... / ;  ^ : 以...开始 ;  $ : 以 ... 结束
     * [3-9] : 范围 3-9 之间
     * \d : 数字, [0-9]
     * {9} : 量词
     */
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入有效的身份证号', trigger: 'blur' }
  ]
});


//编辑
const edit = async (id) => {
  const result = await queryInfoApi(id);
  if(result.code){
    dialogVisible.value = true;
    dialogTitle.value = '修改学员';
    employee.value = result.data;
    employee.value.clazzId = result.data.clazzId;
  }
}

//违纪处理,违纪处理一次，需要将违纪次数+1，违纪扣分+前端输入的分数
const violate = async (id) => {
  violateDialogVisible.value = true;
  violationDialogTitle.value = '违纪处理';
  //传参, 将id传给后端
  student.value.id = id;
}
const saveViolation = async () => {
  let result;
  if(student.value.id){ //修改
    result = await violationHandleApi(student.value.id, student.value.score)
    if(result.code){
      ElMessage.success('违纪处理成功');
      violateDialogVisible.value = false;
      search();
    }else{
      ElMessage.error(result.msg);
    }
  }
}

//删除员工
const deleteById = (id) => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该员工吗?','提示',
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
//记录勾选的员工的id
const selectedIds = ref([]);
//复选框勾选发生变化时触发 - selection: 当前选中的记录 (数组)
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map( item => item.id);
}

//批量删除
const deleteByIds = () => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该员工吗?','提示',
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
  <h1>学员管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" />
      </el-form-item>

      <el-form-item label="学历">
        <el-select v-model="searchEmp.degree" placeholder="请选择">
          <el-option label="初中" value="1" />
          <el-option label="高中" value="2" />
          <el-option label="专科" value="3" />
          <el-option label="本科" value="4" />
          <el-option label="硕士" value="5" />
          <el-option label="博士" value="6" />
        </el-select>
      </el-form-item>

      <el-form-item label="班级ID">
        <el-select v-model="searchEmp.clazzId" placeholder="请选择">
          <el-option v-for="d in clazzsList"
                     :key="d.id"
                     :label="d.name"
                     :value="d.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>

  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addEmp">+ 新增员工</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="stuList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column prop="name" label="姓名" width="120" align="center"/>
      <el-table-column prop="no" label="序号" width="120" align="center"/>
      <el-table-column label="性别" width="90"  align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120"  align="center"/>
      <el-table-column prop="idCard" label="身份证号" width="120"  align="center"/>
      <el-table-column prop="isCollege" label="是否来自院校" width="100"  align="center">
        <template #default="scope">
          {{ scope.row.isCollege == 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column prop="degree" label="学历" width="80"  align="center">
        <template #default="scope">
          <span v-if="scope.row.degree == 1">初中</span>
          <span v-else-if="scope.row.degree == 2">高中</span>
          <span v-else-if="scope.row.degree == 3">大专</span>
          <span v-else-if="scope.row.degree == 4">本科</span>
          <span v-else-if="scope.row.degree == 5">硕士</span>
          <span v-else-if="scope.row.degree == 6">博士</span>
          <span v-else>其他</span>
        </template>
      </el-table-column>
      <el-table-column prop="clazzName" label="班级" width="140"  align="center"/>
      <el-table-column prop="graduationDate" label="毕业日期" width="120"  align="center"/>
      <el-table-column prop="violationCount" label="违纪次数" width="80"  align="center"/>
      <el-table-column prop="violationScore" label="违纪扣分" width="80"  align="center"/>
      <el-table-column prop="updateTime" label="最后操作时间" width="120"  align="center"/>
      <el-table-column label="操作" align="center" width="300">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>
          <el-button type="warning" size="small" @click="violate(scope.row.id)"><el-icon><monitor /></el-icon> 违纪</el-button>
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)"><el-icon><Delete /></el-icon> 删除</el-button>
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

<!--  新增违纪处理对话框-->
  <el-dialog v-model="violateDialogVisible" :title="violationDialogTitle" width="30%">
    <el-form :model="student" label-width="100px">
      <el-form-item label="学员ID">
        <el-input v-model="student.id" disabled></el-input>
      </el-form-item>
      <el-form-item label="违纪扣分">
        <el-input-number v-model="student.score" min="0" max="20" placeholder="请输入违纪扣分(0~20分)"></el-input-number>
      </el-form-item>
    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="violateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveViolation">保存</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 新增员工/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="70%">
    <el-form :model="employee" :rules="rules" ref="empFormRef" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="序号" prop="no">
            <el-input v-model="employee.no" placeholder="请输入学员用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="employee.name" placeholder="请输入学员姓名，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别"  prop="gender">
            <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>

<!--        <el-col :span="12">-->
<!--          <el-form-item label="身份证号" prop="phone">-->
<!--            <el-input v-model="employee.idCard" placeholder="请输入身份证号码"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="学历">
            <el-select v-model="employee.degree" placeholder="请选择学历" style="width: 100%;">
              <el-option v-for="d in degrees" :key="d.value" :label="d.name" :value="d.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="地址">
            <el-input v-model="employee.address" placeholder="请输入地址"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行显示学院的违纪次数和危机扣分，均为短整型数据 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="违纪次数">
            <el-input-number v-model="employee.violationCount" placeholder="请输入违纪次数"></el-input-number>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="违纪扣分">
            <el-input-number v-model="employee.violationScore" placeholder="请输入违纪扣分"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="班级">
            <el-select v-model="employee.clazzId" placeholder="请选择班级" style="width: 100%;">
              <el-option v-for="c in clazzsList"
                         :key="c.id" :label="c.name" :value="c.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="毕业日期">
            <el-date-picker v-model="employee.graduationDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 工作经历 -->
      <!-- 第六行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属院校">
            <el-select v-model="employee.isCollege" placeholder="请选择院校" style="width: 100%;">
              <el-option v-for="c in colleages" :key="c.value" :label="c.name" :value="c.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="身份证号" prop="idCard">
            <el-input v-model="employee.idCard" placeholder="请输入身份证号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>


    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>

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
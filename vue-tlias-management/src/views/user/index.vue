<script setup>
import { ref, watch, onMounted } from 'vue';
import { queryPageApi, addApi, queryInfoApi, updateApi, deleteApi } from '@/api/user';
import { ElMessage, ElMessageBox } from 'element-plus'
import dayjs from "dayjs";


//元数据
//性别列表数据
const genders = ref([{ name: '男', value: "男" }, { name: '女', value: "女" }])


//搜索表单对象
const searchEmp = ref({name: '', sex: '', phone: '', date: [], begin: '', end: ''})

watch(() => searchEmp.value.date, (newVal, oldVal) => {
  if(newVal.length === 2){
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  }else {
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
})

//钩子函数
onMounted(() => {
  search(); //查询员工列表数据
  getToken(); //获取token
})

const token = ref(''); // 初始值为空字符串
//获取token
const getToken = () => {
  try {
    const loginUserStr = localStorage.getItem('loginUser');
    if (!loginUserStr) return; // 没有数据时直接返回

    const loginUser = JSON.parse(loginUserStr); // 先判断非空再解析
    if (loginUser && loginUser.token) {
      token.value = loginUser.token; // Vue3 示例
    }
  } catch (error) {
    console.error('解析 loginUser 失败：', error); // 捕获 JSON 解析错误
  }
}


//查询员工列表
const search = async () => {
  const result = await queryPageApi(searchEmp.value.name, searchEmp.value.sex,searchEmp.value.phone,
                         searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value);
  if(result.code){
    userList.value = result.data.rows;
    total.value = result.data.total;
  }
}

//清空
const clear = () => {
  searchEmp.value = {name: '', sex: '', phone: '', date: [], begin: '', end: ''};
  search();
}

//用户列表数据
const userList = ref([])

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
  dialogTitle.value = '新增员工';
  user.value = {
    userId: '',
    username: '',
    name: '',
    sex: '',
    phone: '',
    birthDate: '',
    image: '',
    createTime: ''
  }
  user.value.createTime = dayjs().format('YYYY-MM-DD HH:mm:ss'); // 自动填充当前时间

  //重置表单的校验规则-提示信息
  if (empFormRef.value){
    empFormRef.value.resetFields();
  }
}

//新增/修改表单
const user = ref({
  userId: '',
  username: '',
  name: '',
  phone: '',
  sex: '',
  birthDate: '',
  image: '',
  createTime: ''
})

// 控制弹窗
const dialogVisible = ref(false)
const dialogTitle = ref('新增用户')

// 文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  user.value.image = response.data;
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}



//保存员工
const save = async () => {
  //表单校验
  if(!empFormRef.value) return;
  empFormRef.value.validate(async (valid) => { //valid 表示是否校验通过: true 通过 / false  不通过
    if(valid){ //通过

      let result;
      if(user.value.id){ //修改
        result = await updateApi(user.value);
      }else { //新增
        result = await addApi(user.value);
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
  userId: [ //身份证号码校验
      { required: true, message: '请输入身份证号码', trigger: 'blur' },
      { pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入有效的身份证号码', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度应在2到20个字符之间', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  sex: [
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
  ]
});


//编辑
const edit = async (id) => {
  const result = await queryInfoApi(id);
  if(result.code){
    dialogVisible.value = true;
    dialogTitle.value = '修改用户';
    user.value = result.data;
  }
}

//记录勾选的员工的id
const selectedIds = ref([]);
//复选框勾选发生变化时触发 - selection: 当前选中的记录 (数组)
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map( item => item.id);
}

//删除员工
const deleteById = (id) => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该用户吗?','提示',
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


//批量删除
const deleteByIds = () => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该用户吗?','提示',
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
  <h1>员工管理</h1>

  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchEmp.name" placeholder="请输入员工姓名" />
      </el-form-item>

      <el-form-item label="性别">
        <el-select v-model="searchEmp.sex" placeholder="请选择用户性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>

      <el-form-item label="电话号码">
        <el-input v-model="searchEmp.phone" placeholder="请输入电话号码" />
      </el-form-item>

      <el-form-item label="创建时间">
        <el-date-picker
          v-model="searchEmp.date"
          type="daterange"
          range-separator="到"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
  
  <!-- 功能按钮 -->
  <div class="container">
    <el-button type="primary" @click="addEmp">+ 新增用户</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 数据展示表格 -->
  <div class="container">
    <el-table :data="userList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column prop="userId" label="用户ID" width="200" align="center"/>
      <el-table-column prop="username" label="用户名" width="120" align="center"/>
      <el-table-column prop="name" label="昵称" width="120" align="center"/>
      <el-table-column prop="sex" label="性别" width="80"  align="center"/>
      <el-table-column label="头像" width="120"  align="center">
        <template #default="scope">
          <img :src="scope.row.image" height="30px">
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话号码" width="120"  align="center"/>
      <el-table-column prop="birthDate" label="出生日期" width="150"  align="center"/>
      <el-table-column prop="createTime" label="创建时间" width="200"  align="center"/>
      <el-table-column prop="updateTime" label="更新时间" width="200"  align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"><el-icon><EditPen /></el-icon> 编辑</el-button>
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
  
  <!-- 新增员工/修改员工的对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form :model="user" :rules="rules" ref="empFormRef" label-width="100px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username" placeholder="请输入用户名，2-20个字"></el-input>
          </el-form-item>
        </el-col>
        
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="user.name" placeholder="请输入用户昵称，2-10个字"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别"  prop="gender">
            <el-select v-model="user.sex" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="user.phone" placeholder="请输入用户手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="出生日期">
            <el-date-picker v-model="user.birthDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="身份证号">
            <el-input v-model="user.userId" placeholder="请输入身份证号"/>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="{'token': token}"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <img v-if="user.image" :src="user.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>


      <!-- 第六行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="创建时间" >
            <el-date-picker
                v-model="user.createTime"
                type="datetime"
                placeholder="选择创建时间"
                style="width: 100%;"
                :disabled="!!user.createTime"
                value-format="YYYY-MM-DD HH:mm:ss"
                default-time="12:00:00">
            </el-date-picker>
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
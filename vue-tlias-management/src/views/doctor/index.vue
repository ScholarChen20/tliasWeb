<script setup>
import { ref, onMounted } from 'vue';
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  queryInfoApi,
} from '@/api/doctor';
import { ElMessage, ElMessageBox } from 'element-plus';
import {Delete, EditPen} from "@element-plus/icons-vue";

//钩子函数
onMounted(() => {
  search();
  getToken();
})

//查询
const docList = ref([])

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
const genders = ref([{ name: '男', value: 1 }, { name: '女', value: 0 }])
const stus = ref([{ name: '可预约', value: 1 }, { name: '不能预约', value: 0 }])
const hospitals = ref([{ name: '深圳大学总医院', value: "深圳大学总医院" }, { name: '北京大学深圳医院', value: "北京大学深圳医院" }])

const titles = ref([{ name: '住院医师', value: "住院医师" },{ name: '主治医师', value: "主治医师" },{ name: '副主任医师', value: "副主任医师" },{ name: '主任医师', value: "主任医师" }])
//性别列表数据
const depts = ref([{ name: '心内科', value: "心内科" }, {  name: '皮肤科', value: "皮肤科" }, {  name: '中医科', value: "中医科" }])

//搜索表单对象
const searchDoc = ref({name: '', gender: '', date: [], dept: '', title: ''})


const search = async () => {
  const result = await queryPageApi(searchDoc.value.name,searchDoc.value.gender,searchDoc.value.dept,searchDoc.value.title,
      currentPage.value,pageSize.value);
  // console.log(result);
  if(result.code){
    docList.value = result.data.rows;
    total.value = result.data.total;
  }
}

//清空
const clear = () => {
  searchDoc.value = {name: '', gender: '', date: [], dept: '', title: ''};
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

// 文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  doc.value.avatar = response.data;
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

// 控制弹窗
const dialogTitle = ref('')

//Dialog对话框
const dialogFormVisible = ref(false);
const formTitle = ref('');
const doc = ref({name: '', dept: '',title: '',hospital: '',gender: '',introduce: '',status: '',phone: '',avatar:''});//新增时默认值

//新增记录
const addRecord = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增医生记录';
  doc.value = {name: '', dept: '',title: '',hospital: '',gender: '',introduce: '',status: '',phone: '',avatar:''}; //新增时默认值

  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

}

//保存
const save = async () => {
  //表单校验
  if(!deptFormRef.value)   ElMessage.error('表单校验不通过');;
  deptFormRef.value.validate(async (valid) => { //valid 表示是否校验通过: true 通过 / false  不通过
    if(valid){ //通过

      let result;
      console.log(doc.value);
      if(doc.value.id){ //修改
        result = await updateApi(doc.value);
      }else{ //新增
        result = await addApi(doc.value);
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
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  dept: [
    { required: true, message: '请选择科室', trigger: 'change' }
  ],
  title: [
    { required: true, message: '请选择职称', trigger: 'change' }
  ],
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
    dialogTitle.value = '修改医生';
    doc.value = result.data;
    console.log(result.data);
  }
}

//删除
const delById = async (id) => {
  //弹出确认框
  ElMessageBox.confirm('您确认删除该医生信息吗?','提示',
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
  ElMessageBox.confirm('您确认删除该医生吗?','提示',
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
  <h1>医生管理</h1>
  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchDoc" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchDoc.name" placeholder="请输入员工姓名" />
      </el-form-item>

      <el-form-item label="性别">
        <el-select v-model="searchDoc.gender" placeholder="请选择">
          <el-option label="男" value="1" />
          <el-option label="女" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="科室">
        <el-select v-model="searchDoc.dept" placeholder="请选择">
          <el-option v-for="d in depts" :key="d.value" :label="d.name" :value="d.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="职称">
        <el-select v-model="searchDoc.title" placeholder="请选择">
          <el-option v-for="t in titles" :key="t.value" :label="t.name" :value="t.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
        <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="container">
    <el-button type="primary" @click="addRecord"> + 新增医生</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="docList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="index" label="序号" width="100" align="center"/>
      <el-table-column prop="name" label="姓名" width="100" align="center"/>
      <el-table-column prop="gender" label="性别" width="100" align="center"/>
      <el-table-column prop="dept" label="科室" width="120" align="center"/>
      <el-table-column prop="title" label="职称" width="130" align="center"/>
      <el-table-column prop="hospital" label="所属医院" width="140" align="center"/>
      <el-table-column prop="introduce" label="简介" width="350" align="center"/>
      <el-table-column prop="status" label="是否可预约" width="100" align="center"/>
      <el-table-column prop="phone" label="电话号" width="150" align="center"/>
      <el-table-column label="头像" width="120"  align="center">
        <template #default="scope">
          <img :src="scope.row.avatar" height="50px">
        </template>
      </el-table-column>
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
    <el-form :model="doc" :rules="rules" ref="deptFormRef" label-width="80px">
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="doc.name" placeholder="请输入姓名，2-20个字"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="科室" prop="dept">
            <el-select v-model="doc.dept" placeholder="请选择科室" style="width: 100%;">
              <el-option v-for="d in depts" :key="dvalue" :label="d.name" :value="d.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别"  prop="gender">
            <el-select v-model="doc.gender" placeholder="请选择性别" style="width: 100%;">
              <el-option v-for="g in genders" :key="g.value" :label="g.name" :value="g.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="doc.phone" placeholder="请输入员工手机号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="职称">
            <el-select v-model="doc.title" placeholder="请选择职称" style="width: 100%;">
              <el-option v-for="t in titles" :key="t.value" :label="t.name" :value="t.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="预约状态">
            <el-select v-model="doc.status" placeholder="是否可预约">
              <el-option v-for="s in stus" :key="s.value" :label="s.name" :value="s.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第四行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="所属医院">
            <el-select v-model="doc.hospital" placeholder="请选择部门" style="width: 100%;">
              <el-option v-for="h in hospitals" :key="h.value" :label="h.name" :value="h.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="20">
          <el-form-item label="头像">
            <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token': token}"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
            >
              <img v-if="doc.avatar" :src="doc.avatar" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 第六行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="简介">
            <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入简介"
                v-model="doc.introduce">
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
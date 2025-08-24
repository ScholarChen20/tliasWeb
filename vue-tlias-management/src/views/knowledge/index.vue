<script setup>
import { ref, onMounted,watch } from 'vue';
import {
  queryPageApi,
  addApi,
  updateApi,
  deleteApi,
  queryInfoApi,
} from '@/api/knowledge';
import { ElMessage, ElMessageBox } from 'element-plus';
import dayjs from "dayjs";

//钩子函数
onMounted(() => {
  search();
  getToken();
})

//查询
const bpList = ref([])
//token
const token = ref('');

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

// 文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  bp.value.image = response.data;
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

//搜索表单对象
const searchEmp = ref({author: '', text: '', date: [], begin: '', end: ''})

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
  const result = await queryPageApi(searchEmp.value.author,searchEmp.value.text,
      searchEmp.value.begin, searchEmp.value.end, currentPage.value, pageSize.value);
  // console.log(result);
  if(result.code){
    bpList.value = result.data.rows;
    total.value = result.data.total;
  }
}

//清空
const clear = () => {
  searchEmp.value = {author: '', text: '', date: [], begin: '', end: ''};
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
const bp = ref({author: '', title: '',text: '',publishTime: '',image: '',updateTime: ''});//新增时默认值

//新增记录
const addRecord = () => {
  dialogFormVisible.value = true;
  formTitle.value = '新增健康知识记录';
  bp.value = {author: '', title: '',text: '',publishTime: '',image: '',updateTime: ''}; //新增时默认值
  bp.value.publishTime =  dayjs().format('YYYY-MM-DD HH:mm:ss'); // 自动填充当前时间
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
  author: [
    { required: true, message: '文章作者必须填', trigger: 'blur' },
  ],
  title: [
    { required: true, message: '文章主题', trigger: 'blur' },
  ],
  text: [
    { required: true, message: '  文章内容必须填', trigger: 'blur' },
  ]

})
const deptFormRef = ref();

//编辑
const edit = async (id) => {
  formTitle.value = '修改健康文章内容';
  //重置表单的校验规则-提示信息
  if (deptFormRef.value){
    deptFormRef.value.resetFields();
  }

  const result = await queryInfoApi(id);
  if(result.code){
    dialogFormVisible.value = true;
    bp.value = result.data;
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
  <h1>文章管理</h1>
  <!-- 搜索栏 -->
  <div class="container">
    <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
      <el-form-item label="作者">
        <el-input v-model="searchEmp.author" placeholder="请输入文章作者" />
      </el-form-item>

      <el-form-item label="内容">
        <el-input v-model="searchEmp.text" placeholder="请输入文章内容" />
      </el-form-item>

      <el-form-item label="发布时间">
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
    <el-button type="primary" @click="addRecord"> + 新增文章</el-button>
  </div>

  <!-- 表格 -->
  <div class="container">
    <el-table :data="bpList" border style="width: 100%">
      <el-table-column type="index" label="序号" width="100" align="center"/>
      <el-table-column prop="author" label="作者" width="150" align="center"/>
      <el-table-column prop="title" label="主题" width="150" align="center"/>
      <el-table-column prop="text" label="内容" width="350" align="center"/>
      <el-table-column prop="image" label="文章图片" width="200" align="center">
        <template #default="scope">
          <img :src="scope.row.image" height="100px">
        </template>
      </el-table-column>
      <el-table-column prop="publishTime" label="发布时间" width="230" align="center"/>
      <el-table-column prop="updateTime" label="更新时间" width="230" align="center"/>
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
  <el-dialog v-model="dialogFormVisible" :title="formTitle" width="600" height="800">
    <el-form :model="bp" :rules="rules" ref="deptFormRef">
      <el-form-item label="作者" label-width="80px" prop="author">
        <el-input v-model="bp.author" />
      </el-form-item>
      <el-form-item label="主题" label-width="80px" prop="title">
        <el-input v-model="bp.title" />
      </el-form-item>
      <el-form-item label="文章图片" label-width="80px">
        <el-upload
            class="avatar-uploader"
            action="/api/upload"
            :headers="{'token': token}"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
          <img v-if="bp.image" :src="bp.image" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="内容" label-width="80px" prop="text">
        <el-input
            type="textarea"
            placeholder="请输入内容"
            :autosize="{ minRows: 2, maxRows: 7}"
            v-model="bp.text"
            maxlength="2000"
            show-word-limit
        />
      </el-form-item>
      <el-form-item label="发布时间" label-width="100px" prop="publishTime">
        <el-date-picker
            v-model="bp.publishTime"
            type="datetime"
            placeholder="选择发布时间"
            :disabled="!!bp.publishTime"
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

<style scoped>
.container {
  margin: 15px 0px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
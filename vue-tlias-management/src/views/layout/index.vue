<script setup>
import {ref, onMounted} from 'vue';
import {ElMessageBox, ElMessage} from 'element-plus';
import { useRouter } from 'vue-router';
import {
  Comment,
  DataAnalysis,
  Document,
  FolderOpened,
  Grid, LocationInformation,
  Opportunity, Place, QuestionFilled,
  Scissor, Setting,
  UserFilled
} from "@element-plus/icons-vue";

//当前登录员工
const loginName = ref('');
const router = useRouter();

//钩子函数
onMounted(() => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.name){
    loginName.value = loginUser.name;
  }
})

//退出登录
const logout = () => {
  //弹出确认框
  ElMessageBox.confirm('您确认退出登录吗?','提示',
    { confirmButtonText: '确认',cancelButtonText: '取消',type: 'warning'}
  ).then(async () => { //确认
    ElMessage.success('退出成功');
    localStorage.removeItem('loginUser');
    //跳转页面-登录
    router.push('/login');
  }).catch(() => { //取消
    ElMessage.info('您已取消退出');
  })
}
</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <img src="@/assets/icon.png" style="width: 40px;position: relative;top: 10px;left: 10px">
        <span class="title">智能后台管理系统</span>
        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> 修改密码 &nbsp;&nbsp;&nbsp; |  &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascript:;" @click="logout">
            <el-icon><SwitchButton /></el-icon> 退出登录 【{{loginName}}】
          </a>
        </span>
      </el-header>
      
      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <!-- 左侧菜单栏 -->
          <el-menu router>
            <!-- 首页菜单 -->
            <el-menu-item index="/index">
              <el-icon><Promotion /></el-icon> 首页
            </el-menu-item>
            
            
            <!-- 班级管理菜单 -->
            <el-sub-menu index="/manage">
              <template #title>
                <el-icon><Menu /></el-icon> 班级学员管理
              </template>
              <el-menu-item index="/clazz">
                <el-icon><HomeFilled /></el-icon>班级管理
              </el-menu-item>
              <el-menu-item index="/stu">
                <el-icon><UserFilled /></el-icon>学员管理
              </el-menu-item>
            </el-sub-menu>
            
            <!-- 系统信息管理 -->
            <el-sub-menu index="/system">
              <template #title>
                <el-icon><Tools /></el-icon>系统信息管理
              </template>
              <el-menu-item index="/dept">
                <el-icon><HelpFilled /></el-icon>部门管理
              </el-menu-item>
              <el-menu-item index="/emp">
                <el-icon><Avatar /></el-icon>员工管理
              </el-menu-item>
            </el-sub-menu>

            <!-- 血压助手管理 -->
            <el-sub-menu index="/assistant">
              <template #title>
                <el-icon><opportunity /></el-icon>血压助手管理
              </template>
              <el-menu-item index="/record">
                <el-icon><data-analysis /></el-icon>血压管理
              </el-menu-item>
              <el-menu-item index="/reserve">
                <el-icon><comment /></el-icon>预约管理
              </el-menu-item>
              <el-menu-item index="/knowledge">
                <el-icon><folder-opened /></el-icon>文章管理
              </el-menu-item>
              <el-menu-item index="/doctor">
                <el-icon><place /></el-icon>医生管理
              </el-menu-item>
              <el-menu-item index="/user">
                <el-icon><user-filled /></el-icon>用户管理
              </el-menu-item>

            </el-sub-menu>

            <!-- 数据统计管理 -->
            <el-sub-menu index="/report">
              <template #title>
                <el-icon><Histogram /></el-icon>数据统计管理
              </template>
              <el-menu-item index="/empReport">
                <el-icon><InfoFilled /></el-icon>员工信息统计
              </el-menu-item>
              <el-menu-item index="/stuReport">
                <el-icon><Share /></el-icon>学员信息统计
              </el-menu-item>
              <el-menu-item index="/log">
                <el-icon><Document /></el-icon>日志信息统计
              </el-menu-item>
            </el-sub-menu>

            <!-- 系统公告 -->
            <el-sub-menu index="/Inform">
              <template #title>
                <el-icon><Setting /></el-icon>系统公告
              </template>
              <el-menu-item index="/inform">
                <el-icon><LocationInformation /></el-icon>公告通知
              </el-menu-item>
              <el-menu-item index="/content">
                <el-icon><QuestionFilled /></el-icon>意见反馈
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
      
    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(to right, #76d636, #5a23e6, #6bdfd9, #2aa6dc, #a8eb12);
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  margin-left: 20px;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool{
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 800px;
}
</style>

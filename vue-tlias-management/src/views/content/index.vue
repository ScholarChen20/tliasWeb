<!-- index.vue -->
<script setup>
import { addApi } from '@/api/log';
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'


// 反馈表单数据
const feedbackForm = ref({
  title: '',
  content: '',
  contact: ''
})

// 表单验证规则
const rules = {
  title: [
    { required: true, message: '请输入反馈标题', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入反馈内容', trigger: 'blur' },
    { min: 5, max: 1000, message: '长度在 10 到 1000 个字符', trigger: 'blur' }
  ],
  contact: [
    { pattern: /^(\w+@[a-zA-Z0-9]+\.[a-zA-Z]+|1[3-9]\d{9})$/, message: '请输入正确的邮箱或手机号', trigger: 'blur' }
  ]
}

// 表单引用
const formRef = ref()

const save = async () => {
  //表单校验
  if(!formRef.value) return;
  formRef.value.validate(async (valid) => { //valid 表示是否校验通过: true 通过 / false  不通过
    if(valid){ //通过

      let result;
      result = await addApi(feedbackForm.value);
      if(result.code) {//成功
        ElMessage.success('提交成功');
        feedbackForm.value = {};
      }else { //失败了
        ElMessage.error(result.msg);
      }
    }else { //不通过
      ElMessage.error('表单校验不通过');
    }
  })
}

// 提交反馈
// const submitFeedback = async () => {
//   if (!formRef.value) return
//
//   await formRef.value.validate(async (valid: boolean) => {
//     if (valid) {
//       try {
//         const response = await axios.post('/api/feedback', feedbackForm.value)
//         if (response.data.code === 1) {
//           ElMessage.success('反馈提交成功！')
//           // 重置表单
//           formRef.value.resetFields()
//         } else {
//           ElMessage.error(response.data.msg || '提交失败')
//         }
//       } catch (error) {
//         ElMessage.error('提交失败，请稍后重试')
//       }
//     }
//   })
// }
</script>

<template>
  <div class="feedback-container">
    <el-card class="feedback-card">
      <template #header>
        <div class="card-header">
          <span>意见反馈</span>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="feedbackForm"
        :rules="rules"
        label-width="200px"
        label-position="right"
      >
        <el-form-item label="反馈标题" prop="title">
          <el-input
            v-model="feedbackForm.title"
            placeholder="请输入反馈标题"
            maxlength="50"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="反馈内容" prop="content">
          <el-input
            v-model="feedbackForm.content"
            type="textarea"
            :rows="6"
            placeholder="请输入您的宝贵意见或建议..."
            maxlength="1000"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="联系方式" prop="contact">
          <el-input
            v-model="feedbackForm.contact"
            placeholder="邮箱或手机号，方便我们联系您"
            maxlength="50"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="save">提交反馈</el-button>
          <el-button @click="formRef?.resetFields()">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="feedback-tips">
        <h4>温馨提示：</h4>
        <ul>
          <li>我们会认真阅读每一条反馈，并尽快回复您</li>
          <li>如有紧急问题，请联系客服热线：400-XXX-XXXX</li>
          <li>请勿提交违法、不实或重复信息</li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.feedback-container {
  padding: 50px;
  max-width: 800px;
  margin: 0 auto;
}

.feedback-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.feedback-tips {
  margin-top: 30px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.feedback-tips h4 {
  margin-top: 0;
  color: #606266;
}

.feedback-tips ul {
  padding-left: 20px;
  color: #909399;
}

.feedback-tips li {
  margin-bottom: 8px;
  line-height: 1.5;
}
</style>

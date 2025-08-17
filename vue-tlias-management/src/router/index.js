import { createRouter, createWebHistory } from 'vue-router'

import IndexView from '@/views/index/index.vue'
import ClazzView from '@/views/clazz/index.vue'
import DeptView from '@/views/dept/index.vue'
import EmpView from '@/views/emp/index.vue'
import LogView from '@/views/log/index.vue'
import StuView from '@/views/stu/index.vue'
import EmpReportView from '@/views/report/emp/index.vue'
import StuReportView from '@/views/report/stu/index.vue'
import LayoutView from '@/views/layout/index.vue'
import LoginView from '@/views/login/index.vue'
import RecordView from '@/views/record/index.vue'
import DoctorView from '@/views/doctor/index.vue'
import ReserveView from '@/views/reserve/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
     path: '/', 
     name: '',
     component: LayoutView,   //LayoutView是所有页面的父组件
     redirect: '/index', //重定向, 默认重定向到index页面
     children: [
      {path: 'index', name: 'index', component: IndexView},
      {path: 'clazz', name: 'clazz', component: ClazzView},
      {path: 'stu', name: 'stu', component: StuView},
      {path: 'dept', name: 'dept', component: DeptView},
      {path: 'emp', name: 'emp', component: EmpView},
      {path: 'log', name: 'log', component: LogView},

      {path: 'record', name: 'record', component: RecordView},
      {path: 'doctor', name: 'doctor', component: DoctorView},
      {path: 'reserve', name: 'reserve', component: ReserveView},


      {path: 'empReport', name: 'empReport', component: EmpReportView},
      {path: 'stuReport', name: 'stuReport', component: StuReportView},
     ]
    },
    {path: '/login', name: 'login', component: LoginView}
  ]
})

export default router

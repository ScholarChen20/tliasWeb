import request from '@/utils/request'

export const empJobData = () => request.get(`/report/empJobData`)

export const empGenderData = () => request.get(`/report/empGenderData`)

export const studentCountData = () => request.get(`/report/studentCountData`)

export const studentDegreeData = () => request.get(`/report/studentDegreeData`)
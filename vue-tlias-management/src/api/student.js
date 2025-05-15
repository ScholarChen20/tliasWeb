import request from '@/utils/request'

export const queryPageApi = (name,degree,clazzId,page,pageSize) =>
  request.get(`/students?name=${name}&degree=${degree}&clazzId=${clazzId}&page=${page}&pageSize=${pageSize}`)

export const queryInfoApi = (id) =>  request.get(`/students/${id}`)

export const addApi = (student) => request.post('/students', student)

export const updateApi = (student) => request.put(`/students`, student)

export const deleteApi = (ids) => request.delete(`/students?ids=${ids}`)

export const violationHandleApi = (id,score) => request.put(`/students/violation/${id}/${score}`)
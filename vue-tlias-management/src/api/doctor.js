import request from '@/utils/request'

export const queryPageApi = (name,gender,dept,title,page,pageSize) =>
    request.get(`/doctors?name=${name}&gender=${gender}&dept=${dept}&title=${title}&page=${page}&pageSize=${pageSize}`)

export const queryInfoApi = (id) => request.get(`/doctors/${id}`);

export const addApi = (doctor) => request.post('/doctors', doctor);

export const updateApi = (doctor) => request.put('/doctors', doctor);

export const deleteApi = (id) => request.delete(`/doctors/id=${id}`);
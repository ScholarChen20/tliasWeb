import request from '@/utils/request'

export const queryPageApi = (userId,dId,status,begin,end,page,pageSize) => request.get
(`/reserves?userId=${userId}&dId=${dId}&status=${status}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

export const queryInfoApi = (id) =>  request.get(`/reserves/${id}`);

export const queryAllApi = () =>  request.get('/reserves/list');

export const queryAdviceApi = (id) => request.get(`/reserves/advice/${id}`)

export const queryUserInfoApi = (id) => request.get(`/reserves/user/${id}`);

export const queryDoctorInfoApi = (id) => request.get(`/reserves/doctor/${id}`);

export const addApi = (reserve) => request.post('/reserves', reserve);

export const updateApi = (reserve) => request.put('/reserves', reserve);

export const deleteApi = (id) => request.delete(`/reserves/id=${id}`);
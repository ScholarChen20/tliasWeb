import request from '@/utils/request'

export const queryPageApi = (name,sex,phone,begin,end,page,pageSize) =>
    request.get(`/users?name=${name}&sex=${sex}&phone=${phone}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

export const queryInfoApi = (id) => request.get(`/users/${id}`);

export const addApi = (user) => request.post('/users', user);

export const updateApi = (user) => request.put('/users', user);

export const deleteApi = (ids) => request.delete(`/users?ids=${ids}`);
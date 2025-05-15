import request from '@/utils/request'

export const queryAllApi = () =>  request.get('/clazzs/list');

export const queryPageApi = (begin,end,page,pageSize) =>
    request.get(`/clazzs?begin=${beigin}&end=${end}&page=${page}&pageSize=${pageSize}`);

export const queryInfoApi = (id) => request.get(`/clazzs/${id}`);

export const addApi = (dept) => request.post('/clazzs', dept);

export const updateApi = (dept) => request.put('/clazzs', dept);

export const deleteApi = (id) => request.delete(`/clazzs/id=${id}`);


export const queryEmp = () => request.get(`/emps/list`);
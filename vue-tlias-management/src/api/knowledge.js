import request from '@/utils/request'

export const queryAllApi = () =>  request.get('/knowledge/list');

export const queryPageApi = (author,text,begin,end,page,pageSize) =>
    request.get(`/knowledge?author=${author}&text=${text}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`);

export const queryInfoApi = (id) => request.get(`/knowledge/${id}`);

export const addApi = (knowledge) => request.post('/knowledge', knowledge);

export const updateApi = (knowledge) => request.put('/knowledge', knowledge);

export const deleteApi = (id) => request.delete(`/knowledge/id=${id}`);
import request from '@/utils/request'

export const queryAllApi = () =>  request.get('/knowledge/list');

export const queryPageApi = (page,pageSize) =>
    request.get(`/knowledge?page=${page}&pageSize=${pageSize}`);

export const queryInfoApi = (id) => request.get(`/knowledge/${id}`);

export const addApi = (knowledge) => request.post('/knowledge', knowledge);

export const updateApi = (knowledge) => request.put('/knowledge', knowledge);

export const deleteApi = (id) => request.delete(`/knowledge/id=${id}`);
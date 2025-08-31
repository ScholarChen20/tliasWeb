import request from '@/utils/request'

export const queryAllApi = () =>  request.get('/userHbpInfos/list');

export const queryInfoApi = (id) =>  request.get(`/userHbpInfos/${id}`);

export const queryPageApi = (userId,begin,end,page,pageSize) =>
    request.get(`/userHbpInfos?userId=${userId}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`);

export const addApi = (usrBhp) => request.post('/userHbpInfos', usrBhp);

export const updateApi = (usrBhp) => request.put('/userHbpInfos', usrBhp);

export const deleteApi = (id) => request.delete(`/userHbpInfos/id=${id}`);

export const queryAllUserApi = () =>  request.get('/users/list');

export const queryWeeklyApi = (id) => request.get(`/userHbpInfos/weekly/${id}`);
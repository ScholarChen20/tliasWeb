import request from '@/utils/request'

export const getInfo = () => {return request.get('/users/{id}')}

export const addApi = (user) => request.post('/users', user);

export const updateApi = (user) => request.put('/users', user);

export const deleteApi = (id) => request.delete(`/users/id=${id}`);
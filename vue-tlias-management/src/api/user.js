import request from '@/utils/request'

export const getInfo = () => {return request.get('/users/{id}')}


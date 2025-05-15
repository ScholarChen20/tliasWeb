import request from '@/utils/request'

export const getLogs = (page,pageSize) =>
    request.get(`/log/page?page=${page}&pageSize=${pageSize}`)
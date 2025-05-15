import request from "@/utils/request";

//查询员工列表数据
export const queryPageApi = (name,gender,begin,end,page,pageSize) => 
  request.get(`/emps?name=${name}&gender=${gender}&begin=${begin}&end=${end}&page=${page}&pageSize=${pageSize}`)

//新增
export const addApi = (emp) =>  request.post('/emps', emp);

//根据ID查询
export const queryInfoApi = (id) =>  request.get(`/emps/${id}`);

//修改
export const updateApi = (emp) =>  request.put('/emps', emp);

//删除
export const deleteApi = (ids) =>  request.delete(`/emps?ids=${ids}`);


package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpLogMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.utils.JwtUtils;
import com.itheima.pojo.*;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogMapper empLogMapper;
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        // 1.查询总记录数
////        Long total = empMapper.count();
////        // 2.分页查询
////        Integer start = (page - 1) * pageSize;// 计算起始索引
////        List<Emp> rows = empMapper.list(start, pageSize);
////        // 3.封装PageResult
////        return new PageResult<Emp>(start,pageSize);
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        /**
         * 使用PageHelper分页插件时，PageResult的构造方法需要传入当前页码和每页显示的记录数
         * PageHelper仅对第一次的查询有效，后续的查询不会分页，需要再次调用startPage
         */
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize()); // 返回当前页码和每页显示的记录数Page对象
        // 查询所有员工信息
        List<Emp> empList = empMapper.listAll(empQueryParam);
        // 解析查询结果，并封装
        Page<Emp> p = (Page<Emp>) empList; // 强制类型转换
        return new PageResult<Emp>(p.getTotal(),p.getResult());  // 获取总记录数和当前页数据
    }

    @Transactional(rollbackFor = Exception.class)  // 事务注解，一旦出现异常，回滚事务，可放在方法上、类上、接口上
    @Override // 默认情况下只有运行异常才会回滚
    public void save(Emp emp) {
        try {
            // 补全员工信息
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.save(emp);
            // 动态插入员工工作经历信息，批量插入
            // foreach循环插入
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //  设置员工id补全工作信息
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
                empExprMapper.saveBatch(exprList);
            }
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
            empLogMapper.insert(empLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        // 删除员工信息
        empMapper.deleteByIds(ids);
        // 删除员工工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        // 查询员工信息
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        // 1.更新员工信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        // 2.更新员工工作经历信息
        // 2.1先删除原有工作经历信息
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        // 2.2再插入新的工作经历信息
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            //  设置员工id补全工作信息
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.saveBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1.调mapper接口
        Emp e = empMapper.selectByUsernameAndPassword(emp);
        //2.判断是否存在这个员工，如果存在组装登录成功信息,如果不存在返回null
        if(e!=null && e.getId()!=null){
            log.info("登录成功,员工信息：{}",e);
            //2.1 生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",e.getId());// 将员工id放入JWT令牌中
            claims.put("username",e.getUsername());// 将员工用户名放入JWT令牌中
            String jwt = JwtUtils.generateJwt(claims);
            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }else{
            log.info("登录失败,用户名或密码错误");
            return null;
        }
    }

    @Override
    public List<Emp> findAll() {
        List<Emp> empList = empMapper.findAll();
        return empList;
    }


}

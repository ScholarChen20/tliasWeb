package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工工作经历Mapper
 */
@Mapper
public interface EmpExprMapper {


    public void saveBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id删除员工工作经历，动态拼接SQL语句
     */
    void deleteByEmpIds(List<Integer> empIds);

    @Select("SELECT * FROM emp_expr WHERE emp_id = #{empId}")
    List<EmpExpr> getByEmpId(Integer empId);


}

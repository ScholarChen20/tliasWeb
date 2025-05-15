package com.itheima.controller;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/depts")   // @RequestMapping注解表示请求路径
// responseBody注解表示返回json数据
@RestController
public class DeptController {
//    private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;
//    @RequestMapping(value = "/depts",method = RequestMethod.GET) // method：指定请求方式为GET
    @GetMapping
    public Result list() {
        log.info("查询所有部门");
        return Result.success(deptService.findAll());
   }

   // PathVariable注解表示路径参数,获取部门id
   @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("修改部门：{}:",id);
        Dept dept =  deptService.getById(id);
        return Result.success(dept);
   }
   // 基于httpservletrequest获取请求参数
//   @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request) {
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//       System.out.println("根据id删除部门：id:"+id);
//        return Result.success();
//    }

    @DeleteMapping  // @RequestParam("id")前端请求参数与服务员端参数一致，可以省略@RequestParam("id") requied默认值是true
    public Result delete(Integer id) {
        log.info("删除部门: {}",id);
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Dept dept) {
        log.info("新增部门：{}",dept);
        deptService.save(dept);
        return Result.success();
    }

    @LogOperation
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门：{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}

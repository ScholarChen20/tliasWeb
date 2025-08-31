package com.itheima.controller;

import com.itheima.pojo.dto.ChartDataDTO;
import com.itheima.pojo.vo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.UserHbp;
import com.itheima.pojo.dto.RecordQueryParam;
import com.itheima.service.UserBhpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequestMapping("/userHbpInfos")
@RestController
public class UserHbpInfoController {
    @Autowired
    private UserBhpService userHbpService;

    /**
     * 条件分页查询列表
     */
    @GetMapping
    public Result page(RecordQueryParam recordQueryParam) {
        log.info("分页模糊查询血压记录page:{},pageSize:{},userid:{},,begin:{},end:{}",
                recordQueryParam.getPage(), recordQueryParam.getPageSize(), recordQueryParam.getUserId(),
                recordQueryParam.getBegin(), recordQueryParam.getEnd());
        PageResult<UserHbp> record = userHbpService.page(recordQueryParam);
        log.info("血压记录列表: {}", record);
        return Result.success(record);
    }
    /**
     * 查询血压记录信息
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询所有血压记录信息");
        List<UserHbp> userHbp = userHbpService.list();
        return Result.success(userHbp);
    }

    @GetMapping("/weekly/{id}")
    public Result getWeeklyInfo(@PathVariable Integer id){
        log.info("查询用户周血压信息：{}",id);
        List<UserHbp> records = userHbpService.getWeeklyInfoById(id);
        // 转换为ECharts所需的数据格式
//        Map<String, ArrayList<String>> chartData = new HashMap<>();
//        chartData.put("dates", new ArrayList<>()); // x轴：日期
//        chartData.put("sbp", new ArrayList<>());   // 收缩压
//        chartData.put("dbp", new ArrayList<>());   // 舒张压
//        chartData.put("heart", new ArrayList<>()); // 心率

        List<String> dates = new ArrayList<>();
        List<Double> sbp = new ArrayList<>();
        List<Double> dbp = new ArrayList<>();
        List<Double> heart = new ArrayList<>();
        for (UserHbp record : records) {
            String date = record.getWriteTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dates.add(date);
            sbp.add(Double.valueOf(record.getSbp()));
            dbp.add(Double.valueOf(record.getDbp()));
            heart.add(Double.valueOf(record.getHeart()));
        }

        return Result.success(new ChartDataDTO(dates, sbp, dbp, heart));
    }
    /**
     * 查询某用户的血压记录
     */
    @GetMapping("/user/{id}")
    public Result getUserInfo(@PathVariable Integer id){
        List<UserHbp> userHbp = userHbpService.getInfoByUserId(id);
        log.info("查询用户血压信息：{}",userHbp);
        return Result.success(userHbp);
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("查询用户信息");
        UserHbp userHbp = userHbpService.getInfo(id);
        return Result.success(userHbp);
    }
    @GetMapping("/latest/{id}")
    public Result getLatestInfo(@PathVariable Integer id){
        log.info("查询患者最新血压信息：{}",id);
        UserHbp userHbp = userHbpService.getInfoById(id);
        return Result.success(userHbp);
    }


    @PostMapping
    public Result save(@RequestBody UserHbp userHbp){
        log.info("新增用户信息:{}",userHbp);
        userHbpService.insert(userHbp);
        return Result.success();
    }

    @PutMapping()
    public Result update(@RequestBody UserHbp userHbp){
        log.info("更新用户信息:{}",userHbp);
        userHbpService.update(userHbp);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除用户信息:{}",ids);
        userHbpService.deleteByIds(ids);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("批量删除用户信息:{}",id);
        userHbpService.delete(id);
        return Result.success();
    }


}

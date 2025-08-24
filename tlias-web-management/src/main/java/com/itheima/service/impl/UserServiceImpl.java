package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.*;
import com.itheima.pojo.dto.UserLoginInfo;
import com.itheima.pojo.dto.UserQueryParam;
import com.itheima.pojo.dto.WxLoginInfo;
import com.itheima.pojo.vo.PageResult;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getInfo(Integer id) {
        return userMapper.list(id);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void save(User user) {
        user.setCreateTime(LocalDateTime.now());
//        user.setUserId(String.valueOf(UUID.randomUUID())); //生成UUID
        userMapper.insert(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public UserLoginInfo login(User user) {
        //1.调mapper接口
        User u = userMapper.selectByUsernameAndPassword(user);
        //2.判断是否存在这个员工，如果存在组装登录成功信息,如果不存在返回null
        if(u!=null && u.getId()!=null){
            log.info("登录成功,员工信息：{}",u);
            //2.1 生成JWT令牌
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",u.getId());// 将员工id放入JWT令牌中
            claims.put("username",u.getUsername());// 将员工用户名放入JWT令牌中
            String jwt = JwtUtils.generateJwt(claims);
            return new UserLoginInfo(u.getId(),u.getUsername(),u.getName(),jwt);
        }else{
            log.info("登录失败,用户名或密码错误");
            return null;
        }
    }

    @Override
    public WxLoginInfo getWxLoginInfo(User user) {
        User u = userMapper.selectByOpenidAndNickname(user);
        if(u!=null && u.getId()!=null){
            log.info("微信登录成功,用户信息：{}",u);
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",u.getId());// 将员工id放入JWT令牌中
            claims.put("openid",u.getOpenid());// 将员工openid放入JWT令牌中
//            claims.put("nickname",u.getNickname());// 将员工昵称放入JWT令牌中
            String jwt = JwtUtils.generateJwt(claims);
            return new WxLoginInfo(u.getId(),u.getOpenid(),jwt);
        }else{
            log.info("微信登录失败,openid或nickname错误");
            return null;
        }
    }

    @Override
    public List<User> list() {
        List<User> list = userMapper.getList();
        return list;
    }

    @Override
    public PageResult<User> page(UserQueryParam userQueryParam) {
        PageHelper.startPage(userQueryParam.getPage(), userQueryParam.getPageSize());
        List<User> list = userMapper.listPage(userQueryParam);
        Page<User> page = (Page<User>)  list;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        userMapper.deleteByIds(ids);
    }

}

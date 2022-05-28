package com.hdu.roommates.service;

import com.hdu.roommates.dao.LoginTicketMapper;
import com.hdu.roommates.dao.StudentInfoMapper;
import com.hdu.roommates.entity.LoginTicket;
import com.hdu.roommates.entity.StudentInfo;
import com.hdu.roommates.util.RoommatesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Service  //userService
public class StudentInfoService {


    @Autowired  // 注入Mapper
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${roommates.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")  // 项目名 "/roommates"
    private String contextPath;


    public StudentInfo findStudentById(int studentId){
        return studentInfoMapper.selectById(studentId);
    }

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    // 登录的时候有成功有失败，失败有很多原因，所以返回值设为Map
    public Map<String, Object> login(int id, String password, int expiredSeconds ){
        /**
         * 登录  用户名 密码 session凭证过期时间
         */
        Map<String, Object> map = new HashMap<>();
        // 空值验证
        if (StringUtils.isBlank(id+"")){
            map.put("username", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("password", "密码不能为空！");
            return map;
        }

        // 验证账号合法性
//        StudentInfo studentInfo = studentInfoMapper.selectTheStudentByName(username);
        StudentInfo studentInfo = studentInfoMapper.selectById(id);
        // 账号是否存在
        if(studentInfo == null){
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }

        // 验证密码  TODO:加盐操作
//        password = RoommatesUtil.md5(password + studentInfo.getSalt());
        if(!studentInfo.getPassword().equals(password)){
            map.put("passwordMsg", "密码错误！");
            return map;
        }
//
//        // 登录成功，生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStudentId(studentInfo.getStudentId());
        loginTicket.setTicket(RoommatesUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        loginTicketMapper.insertLoginTicket(loginTicket);
//
        //这里这张loginTicket表就充当了session作用
        map.put("ticket", loginTicket.getTicket());
        return map;

    }

    // 退出
    public void logout(String ticket){
        // 需要传入凭证，以删除更新状态
        loginTicketMapper.updateStatus(ticket, 1);
    }







    public List<StudentInfo> findStudentInfo(int studentId, int offset, int limit){
        return studentInfoMapper.selectStudentInfo(studentId, offset, limit);
    }
    public List<StudentInfo> findStudentByName(String name, int offset, int limit){
        return studentInfoMapper.selectStudentByName(name, offset, limit);
    }
    public List<StudentInfo> findStudentByLocation(String location, int offset, int limit){
        return studentInfoMapper.selectStudentByLocation(location, offset, limit);
    }
    public int findStudentInfoRows(int studentId){
        return studentInfoMapper.selectStudentRows(studentId);
    }

}
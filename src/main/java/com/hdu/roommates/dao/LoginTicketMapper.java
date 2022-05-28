package com.hdu.roommates.dao;

import com.hdu.roommates.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Mapper
public interface LoginTicketMapper {
    // 使用注解的方式实现sql

    //1.增加一个凭证
    @Insert({
            "insert into login_ticket(student_id, ticket, status, expired) ",
            "values(#{studentId}, #{ticket}, #{status}, #{expired})"
    })// 多个字符串可以自动拼接   // 另外想要让他的id自动生成，需要进行声明
    @Options(useGeneratedKeys = true, keyProperty = "id")   // 主键自动生成，赋给id
    int insertLoginTicket(LoginTicket loginTicket);

    // 查询 依据ticket
    @Select({
            "select id, student_id, ticket, status, expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    // 互联网通常不会删信息，而是修改状态，方面以后留个底
    // 动态sql
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}


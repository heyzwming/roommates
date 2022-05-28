package com.hdu.roommates;

import com.hdu.roommates.dao.LoginTicketMapper;
import com.hdu.roommates.dao.RoomPostMapper;
import com.hdu.roommates.dao.StudentInfoMapper;
import com.hdu.roommates.entity.LoginTicket;
import com.hdu.roommates.entity.RoomPost;
import com.hdu.roommates.entity.StudentInfo;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author xyzhen
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = RoommatesApplication.class)
public class MapperTest {

    @Autowired  // 注入
    private StudentInfoMapper studentInfoMapper;

    @Test
    public void testSelectStudentInfo(){
        //TODO:select出来的数据的student_id为0
        List<StudentInfo> list = studentInfoMapper.selectStudentInfo(0,0,5);
        for (StudentInfo info : list){
            System.out.println(info);
        }
        System.out.println("==============================");
        List<StudentInfo> list1 = studentInfoMapper.selectStudentByName("李国华", 0, 5);
        for (StudentInfo info : list1){
            System.out.println(info);
        }
        System.out.println("++++++++++++++++++++++++++++++");
        List<StudentInfo> list2 = studentInfoMapper.selectStudentByLocation("浙江", 0, 5);
        for (StudentInfo info : list2){
            System.out.println(info);
        }
        System.out.println("-------------------------------");
        int rows = studentInfoMapper.selectStudentRows(0);
        System.out.println(rows);
    }


    //============================
    @Autowired  // 注入
    private RoomPostMapper roomPostMapper;

    @Test
    public void testSelectRoomPost(){
        // 测试查询求组的帖子的信息
        List<RoomPost> list = roomPostMapper.selectRoomPosts(0,0,10);
        for (RoomPost post : list){
            System.out.println(post);
        }

        int rows = roomPostMapper.selectRoomPostRows(0);
        System.out.println(rows);
    }



    @Test
    public void testSelectById(){
        StudentInfo studentInfo = studentInfoMapper.selectById(212060001);
        System.out.println(studentInfo);

    }


    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsertLoginTicket(){

        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setStudentId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));  // 10分钟

        loginTicketMapper.insertLoginTicket(loginTicket);

    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc", 1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }


}

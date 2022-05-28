package com.hdu.roommates.controller;

import com.hdu.roommates.entity.Page;
import com.hdu.roommates.entity.RoomPost;
import com.hdu.roommates.entity.StudentInfo;
import com.hdu.roommates.service.RoomPostService;
import com.hdu.roommates.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Controller
public class HomeController {
    @Autowired
    private RoomPostService roomPostService;

    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){

        page.setRows(roomPostService.findRoomPostRows(0));   // 0:非个人页面  设置总行数
        page.setPath("/index");  // 下一页的返回路径

        // 拿到所有的帖子
        List<RoomPost> list = roomPostService.findRoomPosts(0,page.getOffset(),page.getLimit());

        // 将查到的RoomPost中的studentId转为名字name
        List<Map<String, Object>> roomPosts = new ArrayList<>();

        if(list != null){
            for(RoomPost post : list){
                // 将user的信息添加到discussPost帖子信息中
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                StudentInfo student = studentInfoService.findStudentById(post.getStudentId());
                map.put("student", student);
                roomPosts.add(map);
            }
        }
        // 把要展示的内容装到model中
        model.addAttribute("roomPosts", roomPosts);

        return "/site/index";// 返回模板的路径
    }
}

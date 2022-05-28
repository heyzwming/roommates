package com.hdu.roommates.service;

import com.hdu.roommates.dao.RoomPostMapper;
import com.hdu.roommates.entity.RoomPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Service
public class RoomPostService {
    @Autowired
    private RoomPostMapper roomPostMapper;

    public List<RoomPost> findRoomPosts(int studentId, int offset, int limit){
        return roomPostMapper.selectRoomPosts(studentId, offset, limit);
    }

    public int findRoomPostRows(int studentId){
        return roomPostMapper.selectRoomPostRows(studentId);
    }


}

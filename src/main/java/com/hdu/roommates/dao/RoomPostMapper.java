package com.hdu.roommates.dao;

import com.hdu.roommates.entity.RoomPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Mapper
public interface RoomPostMapper {

    // 1. 查询所有的RoomPost
    List<RoomPost> selectRoomPosts(int studentId, int offset, int limit);

    // 2. 数据长度
    int selectRoomPostRows(@Param("studentId") int studentId);

}

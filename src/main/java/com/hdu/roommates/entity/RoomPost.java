package com.hdu.roommates.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Data
public class RoomPost {
    private int id;
    private int studentId;
    private String title;
    private String content;
    private int type;
    private Date createTime;
}

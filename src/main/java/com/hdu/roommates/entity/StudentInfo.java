package com.hdu.roommates.entity;

import lombok.Data;

/**
 * @Description: 学生个人信息
 * @Author xyzhen
 */
@Data
public class StudentInfo {
    private int id;
    private int studentId;
    private String name;
    private int sex;
    private int studentType;
    private String password;
    private int status;
    private String phone;
    private String major;
    private int roomStatus;
    private int roomId;
    private int bedId;
    private String content;
    private String location;
}

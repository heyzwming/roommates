package com.hdu.roommates.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Data
public class LoginTicket {
    private int id;
    private int studentId;
    private String ticket;
    private int status;
    private Date expired;
}

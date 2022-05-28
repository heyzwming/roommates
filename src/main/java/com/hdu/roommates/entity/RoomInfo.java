package com.hdu.roommates.entity;

import lombok.Data;

/**
 * @Description: 宿舍房间信息
 * @Author xyzhen
 */
@Data
public class RoomInfo {
    private int room_id;
    private int member_now;
    private int ack;
}

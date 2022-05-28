package com.hdu.roommates.entity;

import lombok.Data;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Data
public class Page {
    //当前页码
    private int current = 1;
    // 显示上线
    private int limit = 2;
    // 数据总数(用于计算总页数)  服务端自己查的
    private int rows;
    // 查询路径(用于复用分页链接)
    private String path;


    /**
     * 根据当前页的页码，获取当前页的数据在数据库的起始行
     * 调用查询时需要用到这个方法，获得offset
     */
    public int getOffset() {
        // current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     */
    public int getTotal() {
        // rows / limit [+1]
        if (rows % limit == 0) {   // 整除
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     *  获取起始页码
     *  显示当前页码数的前两页和后两页
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     *  获取结束页码
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }
}

package com.hdu.roommates.util;

/**
 * @Description: TODO
 * @Author xyzhen
 */
public interface RoommatesConstant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    // 重复激活
    int ACTIVATION_REPEAT = 1;

    // 激活失败
    int ACTIVATION_FAILURE = 2;

    // 默认登录凭证超时时间
    int DEFAULT_EXPIRED_SECONDs = 3600 * 1;  // 1 小时

    // 记住密码 登录凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24; // 1天
}

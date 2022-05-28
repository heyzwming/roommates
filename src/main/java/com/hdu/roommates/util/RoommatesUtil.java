package com.hdu.roommates.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Description: TODO
 * @Author xyzhen
 */
public class RoommatesUtil {

    // 随机字符串，用于激活码   上传文件时的随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    // MD5加密
    // 只能加密不能解密
    // 加盐可以增加破解难度
    public static String md5(String key){
        if(StringUtils.isBlank(key)){  // 空串 空值 None 都判为空
            //
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes()); // 十六进制,需要传入bytes
    }

}

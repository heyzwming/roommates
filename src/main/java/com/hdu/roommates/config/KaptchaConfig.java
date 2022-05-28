package com.hdu.roommates.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@Configuration  // 配置类注解
public class KaptchaConfig {
    @Bean  // 声明为Bean  配置核心的组件（是一个接口Producer   有一个默认的实现类，我们需要在方法中实例化这个默认的实现类
    public Producer kaptchaProducer(){
        // properties 对象就是用来封装properties数据的
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"); // 验证码的范围 会自动把String拆开
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");



        DefaultKaptcha kaptcha = new DefaultKaptcha();
        // 传入配置，配置要封装到config对象中
        Config config = new Config(properties);  // 传入properties对象 存的都是key value的map

        kaptcha.setConfig(config);
        return kaptcha;
    }
}

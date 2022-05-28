package com.hdu.roommates;

import com.google.code.kaptcha.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description: TODO
 * @Author xyzhen
 */
@SpringBootTest
@ContextConfiguration(classes = RoommatesApplication.class)
public class KaptchaTest {

    @Autowired
    private Producer kaptchaProducer;

    // 创建日志对象
//    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    // 装配好后，通过创建实例，可以创建字符串和图片
//    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    @Test
    public void getKaptcha(){

        // 手动做输出
        // 生成的验证码服务器端需要记住，好做验证，且不能存在浏览器端，因为比较敏感，使用Session
        // 需要用到配置类产生的Bean（将第三方库，做成Bean）
        // 这个Bean需要autowired
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        System.out.println("---");
        // 将验证码存入session
//        session.setAttribute("kaptcha", text);

        // 将图片输出给浏览器
//        response.setContentType("image/png");   // 写明返回的类型
        // 获得输出流
//        try {
////            OutputStream os = response.getOutputStream();
////            OutputStream os = new FileOutputStream();
//            ImageIO.write(image, "png", os);  // 这个流会由SpringMVC自动关闭
//        } catch (IOException e) {
//            e.printStackTrace();
////            logger.error("响应验证码失败: " + e.getMessage());
//        }
    }

}

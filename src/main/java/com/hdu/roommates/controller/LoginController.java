package com.hdu.roommates.controller;

import com.google.code.kaptcha.Producer;
import com.hdu.roommates.service.StudentInfoService;
import com.hdu.roommates.util.RoommatesConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @Description: 登录页面控制器
 * @Author xyzhen
 */
@Controller
public class LoginController implements RoommatesConstant {

    @Autowired
    private Producer kaptchaProducer;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        // 使用session存验证码
        // 需要用到配置类产生的Bean（将第三方库，做成Bean）
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha", text);

        // 将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);  // 这个流会由SpringMVC自动关闭
        } catch (IOException e) {
//            e.printStackTrace();
            logger.error("响应验证码失败: " + e.getMessage());
        }
    }



    // 注入路径变量
    @Value("${server.servlet.context-path}")
    private String contextPath;   // 接收的成员

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }

    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)  // 根据请求方式不同跟上面的get区分开
    public String login(int username, String password, String code, boolean rememberme, Model model, HttpSession session, HttpServletResponse response){
        // 首先判断验证码
//        String kaptcha = (String) session.getAttribute("kaptcha");
//        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)){
//            model.addAttribute("codeMsg", "验证码不正确!");
//            return "/site/login";   // 返回登录页面
//        }

        // 检查账号密码 交给业务层
        // 看用户有没有勾选记住密码，选上用长的，没选用短的
        int expiredSeconds = rememberme? REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDs;
        Map<String, Object> map = studentInfoService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")){
            // 成功，才会包含ticket
            // 存入cookie
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);   // 在这个路径下的访问都有cookie
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);

            return "redirect:/index";    // 返回首页
        }else {
            // 失败
            // 把错误信息带给页面
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));

            return "/site/login";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){  // 用注解要求spring注入Cookie中的ticket值
        studentInfoService.logout(ticket);

        return "redirect:/login";  // 重新登录
    }
}

package vrbaidu.top.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vrbaidu.top.login.model.User;
import vrbaidu.top.login.service.LoginIService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/11.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private LoginIService loginService;

    @RequestMapping(name = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        User user = loginService.findByUsrId(1L);
        request.setAttribute("name",user);
        return "/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam("uid")Long id) {
        //通过前台传递的ID查询用户记录
        //如果有记录，校验用户信息（账户和密码）
        //页面使用ajax异步验证
        //接收返回信息
        //如果没有，提示登录失败，并提示注册新用户
        User user = loginService.findByUsrId(1L);
        logger.info("====================>"+ user.getStatus());
        return "/index";
    }
}

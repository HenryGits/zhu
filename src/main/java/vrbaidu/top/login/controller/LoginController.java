package vrbaidu.top.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vrbaidu.top.exception.BusinessException;
import vrbaidu.top.login.model.User;
import vrbaidu.top.login.service.LoginIService;
import vrbaidu.top.mq.producer.service.impl.QueueSender;
import vrbaidu.top.mq.producer.service.impl.TopicSender;
import vrbaidu.top.util.DateUtils;

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
    private QueueSender queueSender;
    private TopicSender topicSender;

    @RequestMapping(value = "/login")
    public String login() {
        User user = null;
        User user1 = null;
        user = loginService.findByUsrId(155332752178805997L);
//        user = loginService.getUser(155332752178805997L);
//        user1 = loginService.getUser(7614872954763146461L);
//        if (user1 == null){
//            user1 = loginService.findUserByUsername(7614872954763146461L);
//            loginService.saveUser(user);
//        }
//        if (user == null) {
//            user = loginService.findByUsrId(155332752178805997L);
//            loginService.saveUser(user);
//        }
//        logger.info(user1.getPswd());
          logger.info(user.getNickname());
        return "index";
    }

    @RequestMapping(value = "doLogin",method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @Validated User user) {
        //通过前台传递的ID查询用户记录
        //如果有记录，校验用户信息（账户和密码）
        //页面使用ajax异步验证
        //接收返回信息
        //如果没有，提示登录失败，并提示注册新用户
        //User user = loginService.findByUsrId(1L);
        user.setCreateTime(DateUtils.getNowDateTime());
        try {
            loginService.insertReg(user);
            logger.info("====================>注册成功！");
        }catch (Exception e) {
            BusinessException exception = new BusinessException("注册失败====>"+e.getMessage());
            request.setAttribute("message",exception);
            return "error";
        }
        return "redirect:login";
    }

    /**
     * 发送消息到队列
     * Queue队列：仅有一个订阅者会收到消息，消息一旦被处理就不会存在队列中
     * @param message
     * @return String
     */
    @ResponseBody
    @RequestMapping("queueSender")
    public String queueSender(@RequestParam("message")String message){
        String opt="";
        try {
            queueSender.send("queue1", message);
            opt = "====success====";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }
}

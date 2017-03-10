package vrbaidu.top.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vrbaidu.top.user.model.User;
import vrbaidu.top.user.service.UserIService;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/5.
 */
@Controller
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserIService userService;

    @RequestMapping(value= "/user",method = RequestMethod.GET)
    public String goView(){
        User user = userService.selectByPrimaryKey(1L);
        return "index";
    }
}

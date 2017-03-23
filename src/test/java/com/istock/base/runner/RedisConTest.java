package com.istock.base.runner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import vrbaidu.top.login.model.User;
import vrbaidu.top.login.service.LoginIService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RedisConTest {
    @Resource
    private LoginIService loginService;

    @Test
    public void  conn(){
        Jedis jedis = new Jedis("192.168.1.128", 6379, 0);
        jedis.auth("zhu809683500");
        jedis.set("name", "李四");
        System.out.println(jedis.get("name"));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insert() {
        User user = new User();
        long startDate = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    user.setNickname("name是"+ new Random(8000).nextInt());
                    user.setPswd("密码是"+ new Random(80000).nextFloat());
                    user.setEmail("邮箱是"+new Random(800000).nextDouble());
                    user.setStatus(1L);
                    user.setCreateTime(new Date());
                    user.setLastLoginTime(new Date());
                    loginService.regUser(user);
                }
            }).start();
        }
        System.out.println(System.currentTimeMillis()-startDate);
    }
}

package com.eshopinventory.inventory.manage.user.controller;

import com.alibaba.fastjson.JSON;
import com.eshopinventory.inventory.manage.user.entity.User;
import com.eshopinventory.inventory.manage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/users")
    public List<User> list(){
        List<User> all = userService.getAll();
        stringRedisTemplate.opsForValue().set("user", JSON.toJSONString(all));
        return all;
    }

    @GetMapping("/user")
    public Object user() throws Exception {
        String str = stringRedisTemplate.opsForValue().get("user");
        List<User> users = JSON.parseArray(str, User.class);
        return users;
    }

    @GetMapping("/all")
    public List<User> all(){
        List<User> all = userService.getAll1();
        stringRedisTemplate.opsForValue().set("user", JSON.toJSONString(all));
        return all;
    }

}

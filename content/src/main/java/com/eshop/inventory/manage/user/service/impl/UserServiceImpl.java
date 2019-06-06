package com.eshop.inventory.manage.user.service.impl;

import com.eshop.inventory.manage.user.entity.User;
import com.eshop.inventory.manage.user.mapper.UserMapper;
import com.eshop.inventory.manage.user.repository.UserRepository;
import com.eshop.inventory.manage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }
    @Override
    public  List<User> getAll1(){
        return userRepository.findAll();
    }
}

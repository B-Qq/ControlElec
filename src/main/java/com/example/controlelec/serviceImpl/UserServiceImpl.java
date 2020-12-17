package com.example.controlelec.serviceImpl;

import com.example.controlelec.bean.UserBean;
import com.example.controlelec.mapper.UserMapper;
import com.example.controlelec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean loginIn(String userName, String password) {
        return userMapper.getUuid(userName, password);
    }
}

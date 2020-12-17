package com.example.controlelec.service;

import com.example.controlelec.bean.UserBean;

public interface UserService {
    UserBean loginIn(String userName, String password);
}

package com.example.controlelec.mapper;

import com.example.controlelec.bean.UserBean;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 获取用户UUID
     * @param userName 用户名
     * @param password 密码
     * @return uuid
     */
    UserBean getUuid(@Param("userName") String userName, @Param("password") String password);
}

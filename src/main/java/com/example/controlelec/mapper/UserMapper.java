package com.example.controlelec.mapper;

import com.example.controlelec.bean.UserBean;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    UserBean getUuid(@Param("userName") String userName, @Param("password") String password);
}

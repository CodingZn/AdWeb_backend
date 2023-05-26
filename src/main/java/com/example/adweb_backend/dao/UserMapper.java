package com.example.adweb_backend.dao;

import com.example.adweb_backend.mybatis.po.Auth;
import com.example.adweb_backend.mybatis.po.User;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper {
    User findUserById(int id);

    User findUserByUsername(String username);
    Auth findUserAuthByAuthid(int authid);
}

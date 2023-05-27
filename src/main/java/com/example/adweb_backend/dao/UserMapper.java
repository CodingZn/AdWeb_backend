package com.example.adweb_backend.dao;

import com.example.adweb_backend.mybatis.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserInfoById(int id);

    User findUserByUsername(String username);

    int createUser(User user);

    int updateUser(User user);

    int chooseProfile(int id, int profileID);
}

package com.example.adweb_backend;

import com.example.adweb_backend.mybatis.SqlSessionLoader;
import com.example.adweb_backend.mybatis.po.Auth;
import com.example.adweb_backend.mybatis.po.User;
import com.example.adweb_backend.response.ErrorResponse;
import com.example.adweb_backend.util.JWTToken;
import com.example.adweb_backend.util.PBKDF2;
import org.apache.ibatis.session.SqlSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.adweb_backend.dao.UserMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class UserController {

    UserController(){

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody String username, @RequestBody String password){
        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new ErrorResponse("服务器错误！"), HttpStatus.valueOf(500));
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User result = userMapper.findUserByUsername(username);
        if (result == null){
            sqlSession.close();
            return new ResponseEntity<Object>(new ErrorResponse("无此用户！"), HttpStatus.BAD_REQUEST);
        }

        Auth auth = userMapper.findUserAuthByAuthid(result.getAuthid());
        if (auth==null){
            sqlSession.close();
            return new ResponseEntity<Object>(new ErrorResponse("无此用户！"), HttpStatus.BAD_REQUEST);
        }
        if (!PBKDF2.verify(password, auth.getPasswd(), auth.getSalt())){
            sqlSession.close();
            return new ResponseEntity<Object>(new ErrorResponse("密码错误！"), HttpStatus.BAD_REQUEST);
        }

        String token = JWTToken.getJWT(result.getId(), result.getNickname(), result.getNickname());

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        sqlSession.close();
        return new ResponseEntity<Object>(map, HttpStatus.OK);

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody User user){
        try {
            SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new ErrorResponse("服务器错误！"), HttpStatus.valueOf(500));
        }


        return new ResponseEntity<Object>(new Object(), HttpStatus.OK);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object t5(@RequestBody User user){
        try {
            SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new ErrorResponse("服务器错误！"), HttpStatus.valueOf(500));
        }


        return new ResponseEntity<Object>(new Object(), HttpStatus.OK);
    }

}

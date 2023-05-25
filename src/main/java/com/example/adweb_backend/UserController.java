package com.example.adweb_backend;

import com.example.adweb_backend.mybatis.SqlSessionLoader;
import com.example.adweb_backend.mybatis.po.User;
import com.example.adweb_backend.response.ErrorResponse;
import org.apache.ibatis.session.SqlSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class UserController {
    
    UserController(){

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody User user){
        try {
            SqlSession sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new ErrorResponse("服务器错误！"), HttpStatus.valueOf(500));
        }


        return new ResponseEntity<Object>(new Object(), HttpStatus.OK);
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

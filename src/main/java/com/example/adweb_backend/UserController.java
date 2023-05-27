package com.example.adweb_backend;

import com.example.adweb_backend.mybatis.SqlSessionLoader;

import com.example.adweb_backend.mybatis.po.User;
import com.example.adweb_backend.request.LoginRequest;
import com.example.adweb_backend.request.ProfileidRequest;
import com.example.adweb_backend.request.RegisterRequest;
import com.example.adweb_backend.response.MessageResponse;
import com.example.adweb_backend.util.JWTToken;
import com.example.adweb_backend.util.PBKDF2;

import jakarta.validation.Valid;
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
    public @ResponseBody Object login(@RequestBody @Valid LoginRequest request) {
        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e);
            return new ResponseEntity<Object>(new MessageResponse("服务器错误！"), HttpStatus.valueOf(500));
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        String username = request.getUsername();
        String password = request.getPassword();

        User result = userMapper.findUserByUsername(username);
        if (result == null){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("无此用户！"), HttpStatus.BAD_REQUEST);
        }

        if (!PBKDF2.verify(password, result.getPasswd(), result.getSalt())){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("密码错误！"), HttpStatus.BAD_REQUEST);
        }

        String token = JWTToken.getJWT(result.getId(), result.getUsername(), result.getNickname());

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        sqlSession.close();
        return new ResponseEntity<Object>(map, HttpStatus.OK);

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody @Valid RegisterRequest request){
        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new MessageResponse("服务器错误！"), HttpStatus.valueOf(500));
        }

        String salt = PBKDF2.getSalt();
        String passwd = PBKDF2.getPBKDF2(request.getPassword(), salt);

        User user = new User(0, request.getUsername(), request.getNickname(), request.getPhone(),
                request.getEmail(), salt, passwd, 0);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int result = userMapper.createUser(user);
        sqlSession.commit();

        if(result != 1){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("注册失败！"), HttpStatus.BAD_REQUEST);
        }

//        System.out.println(user.toString());

        sqlSession.close();
        return new ResponseEntity<Object>(new MessageResponse("注册成功！"), HttpStatus.OK);
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Object updateUserinfo(@RequestBody @Valid User user, @RequestHeader(name = "Authorization") String token, @PathVariable String id){
        int tid = JWTToken.verify(token).getClaims().get("id").asInt();
        if (tid != Integer.parseInt(id)){
            System.out.println(tid);
            System.out.println(id);
            return new ResponseEntity<Object>(new MessageResponse("您无权限操作！"), HttpStatus.UNAUTHORIZED);
        }

        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new MessageResponse("服务器错误！"), HttpStatus.valueOf(500));
        }
        user.setId(tid);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int result = userMapper.updateUser(user);
        sqlSession.commit();

        if(result != 1){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("更新失败！"), HttpStatus.BAD_REQUEST);
        }

//        System.out.println(user.toString());

        sqlSession.close();
        return new ResponseEntity<Object>(new MessageResponse("更新成功！"), HttpStatus.OK);

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object getUserInfo(@RequestHeader(name = "Authorization") String token, @PathVariable String id){
        int tid = JWTToken.verify(token).getClaims().get("id").asInt();
        if (tid != Integer.parseInt(id)){
            System.out.println(tid);
            System.out.println(id);
            return new ResponseEntity<Object>(new MessageResponse("您无权限操作！"), HttpStatus.UNAUTHORIZED);
        }

        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new MessageResponse("服务器错误！"), HttpStatus.valueOf(500));
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User result = userMapper.findUserInfoById(tid);

        if(result == null){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("用户不存在！"), HttpStatus.NOT_FOUND);
        }

//        System.out.println(user.toString());

        sqlSession.close();
        return new ResponseEntity<Object>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
    public Object checkUsername(@RequestParam String username){
        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new MessageResponse("服务器错误！"), HttpStatus.valueOf(500));
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User result = userMapper.findUserByUsername(username);

        if(result != null){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("用户名已被占用！"), HttpStatus.BAD_REQUEST);
        }

//        System.out.println(user.toString());

        sqlSession.close();
        return new ResponseEntity<Object>(new MessageResponse("用户名可用！"), HttpStatus.OK);

    }

    @RequestMapping(value = "/user/{id}/profile", method = RequestMethod.POST)
    public Object chooseProfile(@RequestHeader(name = "Authorization") String token, @PathVariable String id, @RequestBody ProfileidRequest request){
        int tid = JWTToken.verify(token).getClaims().get("id").asInt();
        if (tid != Integer.parseInt(id)){
            System.out.println(tid);
            System.out.println(id);
            return new ResponseEntity<Object>(new MessageResponse("您无权限操作！"), HttpStatus.UNAUTHORIZED);
        }

        SqlSession sqlSession;
        try {
            sqlSession = SqlSessionLoader.getSqlSession();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<Object>(new MessageResponse("服务器错误！"), HttpStatus.valueOf(500));
        }

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(request.getProfileID());
        int result = userMapper.chooseProfile(tid, request.getProfileID());
        System.out.println(result);
        sqlSession.commit();

        if(result != 1){
            sqlSession.close();
            return new ResponseEntity<Object>(new MessageResponse("操作失败！"), HttpStatus.NOT_FOUND);
        }

//        System.out.println(user.toString());

        sqlSession.close();
        return new ResponseEntity<Object>(new MessageResponse("操作成功！"), HttpStatus.OK);

    }

}

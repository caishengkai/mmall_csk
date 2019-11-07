package com.csk.mmall.dao;

import com.csk.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Resource
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User checkLogin(String username, @Param("password") String md5Password);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);

    int checkAnswer(String username, String question, String answer);

    int updatePasswordByUsername(String username, @Param("password") String md5Password);

    int checkPassword(@Param("password") String md5EncodeUtf8, @Param("userId") Integer id);

    int checkEmailByUserId(String email, @Param("userId") Integer id);
}
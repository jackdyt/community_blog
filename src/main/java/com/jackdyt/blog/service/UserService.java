package com.jackdyt.blog.service;

import com.jackdyt.blog.mapper.UserMapper;
import com.jackdyt.blog.model.User;
import com.jackdyt.blog.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void check(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = new User();
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(dbUser, example);
        }
    }
}

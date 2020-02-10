package com.lhy.blog.service;

import com.lhy.blog.dao.UserRepository;
import com.lhy.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User getUser() {
        return userRepository.getOne((long) 1);
    }
}

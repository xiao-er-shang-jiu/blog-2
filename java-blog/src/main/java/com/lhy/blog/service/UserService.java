package com.lhy.blog.service;

import com.lhy.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);

    User getUser();
}

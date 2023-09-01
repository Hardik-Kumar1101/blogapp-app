package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.User;

public interface UserService {

    boolean existsByName(String name);

    void save(User user);

    void delete(String email);

    void update(User user);

}

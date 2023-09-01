package com.blogwebsite.blogwebapp.service;

import com.blogwebsite.blogwebapp.model.User;
import com.blogwebsite.blogwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByName(String name) {
        return userRepository.findByUsername(name) != null ? true : false;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}

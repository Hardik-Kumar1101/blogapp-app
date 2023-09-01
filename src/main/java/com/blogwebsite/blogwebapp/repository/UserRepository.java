package com.blogwebsite.blogwebapp.repository;

import com.blogwebsite.blogwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    User findByEmail(String email);

    void delete(User user);

    User save(User user);
}

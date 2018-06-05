package com.steveq.kidsmotivator.app.auth.dao;

import com.steveq.kidsmotivator.app.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
    List<User> findAllByParentsContains(List<User> parents);
    User findUserById(Integer id);
}

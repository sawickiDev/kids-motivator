package com.steveq.kidsmotivator.app.persistence.dao;

import com.steveq.kidsmotivator.app.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);

}

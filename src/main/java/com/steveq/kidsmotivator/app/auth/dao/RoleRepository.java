package com.steveq.kidsmotivator.app.auth.dao;

import com.steveq.kidsmotivator.app.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRole (String roleName);
}

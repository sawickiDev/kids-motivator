package com.steveq.kidsmotivator.app.persistence.dao;

import com.steveq.kidsmotivator.app.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRole (String roleName);
}

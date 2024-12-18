package com.example.spring_security.repository;

import com.example.spring_security.entity.Role;
import com.example.spring_security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}

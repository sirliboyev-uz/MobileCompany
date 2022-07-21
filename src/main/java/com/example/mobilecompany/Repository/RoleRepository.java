package com.example.mobilecompany.Repository;

import com.example.mobilecompany.Enums.RoleName;
import com.example.mobilecompany.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    boolean existsByRoleName(RoleName roleName);

    Optional<Roles> findByRoleName(RoleName roleName);
}

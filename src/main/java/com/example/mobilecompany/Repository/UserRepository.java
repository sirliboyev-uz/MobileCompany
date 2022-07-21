package com.example.mobilecompany.Repository;

import com.example.mobilecompany.Model.Roles;
import com.example.mobilecompany.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByRoles(Roles roles);
}

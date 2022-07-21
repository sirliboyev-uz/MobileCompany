package com.example.mobilecompany.Repository;

import com.example.mobilecompany.Model.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefinitionRepository  extends JpaRepository<Definition, Integer> {
    Optional<Definition> findByName(String name);
}

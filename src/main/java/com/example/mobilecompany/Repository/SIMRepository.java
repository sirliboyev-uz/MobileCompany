package com.example.mobilecompany.Repository;

import com.example.mobilecompany.Model.SIMCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SIMRepository extends JpaRepository<SIMCard, Integer> {
    Optional<SIMCard> findBySimNumber(String simNumber);
}

package com.huzzi.capstone.AuthService.repository;

import com.huzzi.capstone.AuthService.modal.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndExpiryDateGreaterThan(String tokenValue, LocalDateTime expiryDate);
}

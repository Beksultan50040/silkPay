package com.example.silkpaytask.repository;


import com.example.silkpaytask.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByEmail(String login);
}

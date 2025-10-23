package com.example.angelneria_final_project.domain.repositories;

import com.example.angelneria_final_project.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE email =:email", nativeQuery = true)
    Optional<User> findByEmailIncludingInactives(String email);
    List<User> findByCountryCode(String code);
    Optional<User> findByEmail(String email);
}

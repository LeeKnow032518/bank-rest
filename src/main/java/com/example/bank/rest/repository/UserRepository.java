package com.example.bank.rest.repository;

import com.example.bank.rest.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    Optional<UserDetails> findByUsername(String username);
}

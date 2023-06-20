package com.group2.onvidapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group2.onvidapp.entity.User;

public interface UserReposioty extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
}

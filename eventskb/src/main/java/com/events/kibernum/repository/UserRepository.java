package com.events.kibernum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import com.events.kibernum.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByEmail(String email);
    
}
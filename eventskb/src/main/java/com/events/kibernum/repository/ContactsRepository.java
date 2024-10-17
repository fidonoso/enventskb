package com.events.kibernum.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.events.kibernum.model.Contacts;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, UUID> {
    List<Contacts> findByUserId(UUID userId);
}


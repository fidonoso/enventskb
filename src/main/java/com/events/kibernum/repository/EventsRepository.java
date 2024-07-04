package com.events.kibernum.repository;

import com.events.kibernum.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EventsRepository extends JpaRepository<Events, UUID> {
}


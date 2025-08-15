package com.rohitp.finditserver.repository;

import com.rohitp.finditserver.model.Session;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends Repository<Session, UUID> {

    Optional<Session> findById(UUID id);

    Session save(Session session);

}

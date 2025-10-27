package com.rohitp.finditserver.repository;

import com.rohitp.finditserver.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    Page<User> findAll(@NonNull Pageable pageable);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    User save(User user);

    @Transactional
    Integer deleteUserById(UUID id);

}

package com.rohitp.finditserver.repository;

import com.rohitp.finditserver.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {

    List<User> findAll();

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    User save(User user);

    @Transactional
    Integer deleteUserById(Integer id);

}

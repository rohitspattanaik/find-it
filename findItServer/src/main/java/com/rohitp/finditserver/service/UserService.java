package com.rohitp.finditserver.service;

import com.rohitp.finditserver.dto.user.CreateUserRequest;
import com.rohitp.finditserver.dto.user.UpdateUserRequest;
import com.rohitp.finditserver.dto.user.UserSearchParams;
import com.rohitp.finditserver.exception.user.UserConflictException;
import com.rohitp.finditserver.exception.user.UserNotFoundException;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public static final int LIST_PAGE_SIZE = 10;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(UserSearchParams searchParams) {
        int offset = searchParams != null ? searchParams.getNextPageToken().orElse(0) : 0;
        int page = offset / LIST_PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, LIST_PAGE_SIZE);

        return this.userRepository.findAll(pageable);
    }

    public User getUserById(UUID id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByEmail(String email) {
        return this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public User createUser(CreateUserRequest createUserRequest) {
        try {
            return this.userRepository.save(
                    User
                            .builder()
                            .firstName(createUserRequest.getFirstName())
                            .lastName(createUserRequest.getLastName())
                            .email(createUserRequest.getEmail())
                            .build());
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) { // postgresql error message
                throw new UserConflictException(createUserRequest.getEmail(), e);
            } else {
                throw e;
            }
        }


    }

    public User updateUser(UUID id, UpdateUserRequest updateUserRequest) {
        User existingUser = getUserById(id);

        return this.userRepository.save(
                existingUser.toBuilder()
                        .firstName(updateUserRequest.getFirstName().orElse(existingUser.getFirstName()))
                        .lastName(updateUserRequest.getLastName().orElse(existingUser.getLastName()))
                        .email(updateUserRequest.getEmail().orElse(existingUser.getEmail()))
                        .build());
    }

    public void deleteUser(UUID id) {
        Integer deleteCount = this.userRepository.deleteUserById(id);
        if (deleteCount == 0) {
            throw new UserNotFoundException(id);
        }
    }
}

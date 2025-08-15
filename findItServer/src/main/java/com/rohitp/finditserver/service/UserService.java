package com.rohitp.finditserver.service;

import com.rohitp.finditserver.dto.user.CreateUserRequest;
import com.rohitp.finditserver.dto.user.UpdateUserRequest;
import com.rohitp.finditserver.exception.user.UserConflictException;
import com.rohitp.finditserver.exception.user.UserNotFoundException;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) {
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

    public User updateUser(Integer id, UpdateUserRequest updateUserRequest) {
        User existingUser = getUserById(id);

        return this.userRepository.save(
                existingUser.toBuilder()
                        .firstName(updateUserRequest.getFirstName().orElse(existingUser.getFirstName()))
                        .lastName(updateUserRequest.getLastName().orElse(existingUser.getLastName()))
                        .email(updateUserRequest.getEmail().orElse(existingUser.getEmail()))
                        .build());
    }

    public void deleteUser(Integer id) {
        Integer deleteCount = this.userRepository.deleteUserById(id);
        if (deleteCount == 0) {
            throw new UserNotFoundException(id);
        }
    }
}

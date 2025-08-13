package com.rohitp.finditserver.controller;

import com.rohitp.finditserver.dto.user.CreateUserRequest;
import com.rohitp.finditserver.dto.user.UpdateUserRequest;
import com.rohitp.finditserver.dto.user.UserDTO;
import com.rohitp.finditserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public UserDTO getUser(@PathVariable Integer userId) {
        return UserDTO.fromUser(this.userService.getUserById(userId));
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return UserDTO.fromUser(this.userService.createUser(createUserRequest));
    }

    @PatchMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public UserDTO updateUser(@PathVariable Integer userId, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return UserDTO.fromUser(this.userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
    }

}

package com.rohitp.finditserver.controller;

import com.rohitp.finditserver.dto.PaginationDTO;
import com.rohitp.finditserver.dto.user.*;
import com.rohitp.finditserver.filter.SessionFilter;
import com.rohitp.finditserver.model.User;
import com.rohitp.finditserver.service.UserService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.rohitp.finditserver.service.UserService.LIST_PAGE_SIZE;

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
    public UserDTO getUser(@PathVariable UUID userId) {
        return UserDTO.fromUser(this.userService.getUserById(userId));
    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    @PreAuthorize(SessionFilter.HAS_ADMIN_AUTHORITY)
    public ListUsersResponse getUsers(@Nullable UserSearchParams searchParams) {
        Page<User> userPage = this.userService.getAllUsers(searchParams);

        // Calculate the next page offset
        long nextOffset = userPage.getPageable().getOffset() + LIST_PAGE_SIZE;

        return ListUsersResponse
                .builder()
                .users(userPage.getContent().stream().map(UserDTO::fromUser).toList())
                .pagination(PaginationDTO
                        .builder()
                        .currentPageToken(userPage.getPageable().getOffset())
                        .nextPageToken(nextOffset > userPage.getTotalElements() ? null : nextOffset)
                        .totalSize(userPage.getTotalElements())
                        .pageSize((long) LIST_PAGE_SIZE)
                        .build())

                .build();
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return UserDTO.fromUser(this.userService.createUser(createUserRequest));
    }

    @PatchMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public UserDTO updateUser(@PathVariable UUID userId, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return UserDTO.fromUser(this.userService.updateUser(userId, updateUserRequest));
    }

    @DeleteMapping(value = "/{userId}", produces = "application/json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        this.userService.deleteUser(userId);
    }

}

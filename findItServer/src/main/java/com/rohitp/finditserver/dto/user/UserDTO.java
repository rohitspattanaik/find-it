package com.rohitp.finditserver.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rohitp.finditserver.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
public class UserDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore()
    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .build();
    }

}

package com.rohitp.finditserver.dto.user;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Data
@Builder
public class ListUsersResponse {

    private List<UserDTO> users;

    @Nullable
    private String nextPageToken;

}

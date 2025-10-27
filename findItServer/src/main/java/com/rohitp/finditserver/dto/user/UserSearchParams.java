package com.rohitp.finditserver.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Optional;

@Jacksonized
@Data
@Builder
public class UserSearchParams {

    @Builder.Default
    Optional<Boolean> isAdmin = Optional.empty();

    @Builder.Default
    Optional<String> email = Optional.empty();

    @Builder.Default
    Optional<Integer> nextPageToken = Optional.empty();
}

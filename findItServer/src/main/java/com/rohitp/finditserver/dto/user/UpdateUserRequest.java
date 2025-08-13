package com.rohitp.finditserver.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Optional;

@Jacksonized
@Builder
@JsonSerialize
@Data
public class UpdateUserRequest {

    @Builder.Default
    private Optional<String> firstName = Optional.empty();

    @Builder.Default
    private Optional<String> lastName = Optional.empty();

    @Builder.Default
    private Optional<String> email = Optional.empty();

}

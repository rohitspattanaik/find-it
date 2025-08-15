package com.rohitp.finditserver.dto.authentication;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonSerialize
@Data
public class AuthenticationRequest {

    @NotBlank(message = "Email is required")
    private String email;

}

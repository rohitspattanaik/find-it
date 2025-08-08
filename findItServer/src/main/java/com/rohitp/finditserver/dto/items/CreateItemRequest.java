package com.rohitp.finditserver.dto.items;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonSerialize
@Data
public class CreateItemRequest {

    @NotBlank
    private String name;

    @Nullable
    private String description;

}

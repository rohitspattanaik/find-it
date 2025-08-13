package com.rohitp.finditserver.dto.item;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Optional;

@Jacksonized
@Builder
@JsonSerialize
@Data
public class UpdateItemRequest {

    @Builder.Default
    private Optional<String> name = Optional.empty();

    @Builder.Default
    private Optional<String> description = Optional.empty();

}

package com.rohitp.finditserver.dto.exception;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
public class ExceptionDTO {

    private String timestamp;

    private Integer status;

    @Nullable
    private String message;

}

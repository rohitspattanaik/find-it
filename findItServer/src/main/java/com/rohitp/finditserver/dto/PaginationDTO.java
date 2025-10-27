package com.rohitp.finditserver.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
public class PaginationDTO {

    @Nullable
    private Long nextPageToken;

    private Long currentPageToken;

    @Nullable
    private Long totalSize;

    @Nullable
    private Long pageSize;

}

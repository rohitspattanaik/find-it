package com.rohitp.finditserver.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rohitp.finditserver.model.Session;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

@Jacksonized
@Data
@Builder
public class SessionDTO {

    private UUID id;

    private UUID userId;

    private Boolean isAdmin;

    private LocalDateTime expiresAt;

    @JsonIgnore
    public static SessionDTO fromSession(Session session) {
        return SessionDTO.builder()
                .id(session.getId())
                .userId(session.getUserId())
                .isAdmin(session.getIsAdmin())
                .expiresAt(session.getExpiresAt())
                .build();
    }

}

package com.rohitp.finditserver.dto.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rohitp.finditserver.model.Item;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Jacksonized
@Data
@Builder
public class ItemDTO {

    private UUID id;

    private String name;

    private String description;

    @JsonIgnore
    public static ItemDTO fromItem(Item item) {
        return ItemDTO
                .builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }

}

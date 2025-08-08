package com.rohitp.finditserver.dto.items;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rohitp.finditserver.model.Item;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
public class ItemDTO {

    private Integer id;

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

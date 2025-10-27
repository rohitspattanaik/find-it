package com.rohitp.finditserver.repository;

import com.rohitp.finditserver.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends Repository<Item, UUID> {

    Optional<Item> findById(UUID id);

    Item save(Item item);

    @Transactional
    Integer deleteItemById(UUID id);

}

package com.rohitp.finditserver.repository;

import com.rohitp.finditserver.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ItemRepository extends Repository<Item, Integer> {

    Optional<Item> findById(Integer id);

    Item save(Item item);

    @Transactional
    Integer deleteItemById(Integer id);

}

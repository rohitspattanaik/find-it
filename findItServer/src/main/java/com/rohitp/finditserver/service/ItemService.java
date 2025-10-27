package com.rohitp.finditserver.service;

import com.rohitp.finditserver.dto.item.CreateItemRequest;
import com.rohitp.finditserver.dto.item.UpdateItemRequest;
import com.rohitp.finditserver.exception.item.ItemNotFoundException;
import com.rohitp.finditserver.model.Item;
import com.rohitp.finditserver.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(UUID id) {
        return this.itemRepository
                .findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public Item createItem(CreateItemRequest createItemRequest) {
        return this.itemRepository.save(
                    Item
                            .builder()
                            .name(createItemRequest.getName())
                            .description(createItemRequest.getDescription())
                            .build());
    }

    public Item updateItem(UUID id, UpdateItemRequest updateItemRequest) {
        Item existingItem = getItemById(id);

        return this.itemRepository.save(
                existingItem
                        .toBuilder()
                        .name(updateItemRequest.getName().orElse(existingItem.getName()))
                        .description(updateItemRequest.getDescription().orElse(existingItem.getDescription()))
                        .build());
    }

    public void deleteItem(UUID id) {
        Integer deleteCount = this.itemRepository.deleteItemById(id);

        System.out.println(deleteCount);
    }

}

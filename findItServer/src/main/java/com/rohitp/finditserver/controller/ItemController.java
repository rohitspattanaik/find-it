package com.rohitp.finditserver.controller;

import com.rohitp.finditserver.dto.item.CreateItemRequest;
import com.rohitp.finditserver.dto.item.ItemDTO;
import com.rohitp.finditserver.dto.item.UpdateItemRequest;
import com.rohitp.finditserver.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/{itemId}", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public ItemDTO getItem(@PathVariable Integer itemId) {
        return ItemDTO.fromItem(this.itemService.getItemById(itemId));
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemDTO createItem(@RequestBody @Valid CreateItemRequest createItemRequest) {
        return ItemDTO.fromItem(this.itemService.createItem(createItemRequest));
    }

    @PatchMapping(value = "/{itemId}", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public ItemDTO updateItem(@PathVariable Integer itemId, @RequestBody @Valid UpdateItemRequest updateItemRequest) {
        return ItemDTO.fromItem(this.itemService.updateItem(itemId, updateItemRequest));
    }

    @DeleteMapping(value = "/{itemId}", produces = "application/json")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public void deleteItem(@PathVariable Integer itemId) {
        this.itemService.deleteItem(itemId);
    }

}

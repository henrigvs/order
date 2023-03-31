package com.switchfully.eurder.item.api;


import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.item.service.dtos.CreateItemDTO;
import com.switchfully.eurder.item.service.dtos.ItemDTO;
import com.switchfully.eurder.item.service.dtos.UpdateItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }


    /** POST */

    @PostMapping(path = "/item", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO saveItem(@RequestBody CreateItemDTO createItemDTO, @RequestHeader String adminId){
        return itemService.saveItem(createItemDTO, adminId);
    }

    @PutMapping(path = "/item/{itemId}", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ItemDTO updateItem(@RequestBody UpdateItemDTO updateItemDTO, @PathVariable String itemId, @RequestHeader String adminId){
        return itemService.updateItem(updateItemDTO, itemId, adminId);
    }

    /** GET */

    @GetMapping(path = "/allItems", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getAllItems(@RequestHeader String adminId){
        return itemService.getAllItems(adminId);
    }

    @GetMapping(path = "/item/{itemId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ItemDTO getItemById(@PathVariable String itemId){
        return itemService.getItemById(itemId);
    }

    @GetMapping(path = "/lowStock", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getStockLow(@RequestHeader String adminId){
        return itemService.getStockLow(adminId);
    }

    @GetMapping(path = "/mediumStock", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getStockMedium(@RequestHeader String adminId){
        return itemService.getStockMedium(adminId);
    }

    @GetMapping(path = "/highStock", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getStockHigh(@RequestHeader String adminId){
        return itemService.getStockHigh(adminId);
    }
}

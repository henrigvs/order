package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.service.dtos.CreateItemDTO;
import com.switchfully.eurder.item.service.dtos.ItemDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public ItemDTO toDTO(Item item){
        if(item == null)
            return null;
        else
            return new ItemDTO()
                .setId(item.getItemId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setPrice(item.getPrice())
                .setAmount(item.getAmount());
    }

    public Item toEntity(CreateItemDTO createItemDTO){
        return new Item.Builder()
                .withName(createItemDTO.name())
                .withDescription(createItemDTO.description())
                .withPrice(createItemDTO.price())
                .withAmount(createItemDTO.amount())
                .build();
    }

    public List<ItemDTO> toDTOList(List<Item> itemList){
        return itemList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

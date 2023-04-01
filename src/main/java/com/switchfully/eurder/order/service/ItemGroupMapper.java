package com.switchfully.eurder.order.service;

import com.switchfully.eurder.order.domain.ItemGroup;
import com.switchfully.eurder.order.service.dtos.itemgroupdtos.ItemGroupDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ItemGroupMapper {

    public ItemGroupDTO toDTO(ItemGroup itemGroup){
        return new ItemGroupDTO(itemGroup.getItemId(), itemGroup.getPrice(), itemGroup.getAmountOrdered(), itemGroup.getShippingDate());
    }

    public List<ItemGroupDTO> toDTOList(List<ItemGroup> itemGroupList){
        return itemGroupList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ItemGroup toEntity(ItemGroupDTO itemGroupDTO){
        return new ItemGroup(itemGroupDTO.itemId(), itemGroupDTO.price(), itemGroupDTO.amountOrdered(), itemGroupDTO.shippingDate());
    }

    public List<ItemGroup> toEntityList(List<ItemGroupDTO> itemGroupDTOList){
        return itemGroupDTOList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.service.dtos.CreateItemDTO;
import com.switchfully.eurder.item.service.dtos.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ItemMapperTest {

    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        itemMapper = new ItemMapper();
    }

    @Test
    void toDTO() {
        Item item = new Item.Builder().withName("Item 1").withDescription("Item 1 description").withPrice(10.5).withAmount(100).build();
        ItemDTO itemDTO = itemMapper.toDTO(item);

        assertNotNull(itemDTO.getItemId());
        assertEquals(item.getName(), itemDTO.getName());
        assertEquals(item.getDescription(), itemDTO.getDescription());
        assertEquals(item.getPrice(), itemDTO.getPrice());
        assertEquals(item.getAmount(), itemDTO.getAmount());
    }

    @Test
    void toEntity() {
        CreateItemDTO createItemDTO = new CreateItemDTO("Item1", "Item 1 description", 10.5, 100);
        Item item = itemMapper.toEntity(createItemDTO);

        assertNotNull(item.getItemId());
        assertEquals(createItemDTO.name(), item.getName());
        assertEquals(createItemDTO.description(), item.getDescription());
        assertEquals(createItemDTO.price(), item.getPrice());
        assertEquals(createItemDTO.amount(), item.getAmount());
    }

    @Test
    void toDTOList() {
        Item item1 = new Item.Builder().withName("Item 1").withDescription("Item 1 description").withPrice(10.5).withAmount(100).build();
        Item item2 = new Item.Builder().withName("Item 2").withDescription("Item 2 description").withPrice(15.5).withAmount(50).build();
        List<Item> itemList = Arrays.asList(item1, item2);
        List<ItemDTO> itemDTOList = itemMapper.toDTOList(itemList);

        assertEquals(2, itemDTOList.size());
        assertNotNull(itemDTOList.get(0));
        assertEquals(item1.getName(), itemDTOList.get(0).getName());
        assertEquals(item1.getDescription(), itemDTOList.get(0).getDescription());
        assertEquals(item1.getPrice(), itemDTOList.get(0).getPrice());
        assertEquals(item1.getAmount(), itemDTOList.get(0).getAmount());
        assertNotNull(itemDTOList.get(1));
        assertEquals(item2.getName(), itemDTOList.get(1).getName());
        assertEquals(item2.getDescription(), itemDTOList.get(1).getDescription());
        assertEquals(item2.getPrice(), itemDTOList.get(1).getPrice());
        assertEquals(item2.getAmount(), itemDTOList.get(1).getAmount());
    }
}

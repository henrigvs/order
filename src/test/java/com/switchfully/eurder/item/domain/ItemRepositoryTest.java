package com.switchfully.eurder.item.domain;

import com.google.common.collect.Lists;
import com.switchfully.eurder.item.domain.exceptions.ItemAlreadyExistException;
import com.switchfully.eurder.item.domain.exceptions.ItemIdDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    private ItemRepository itemRepository;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepository();

        item1 = new Item.Builder()
                .withName("Item1")
                .withDescription("Item1 description")
                .withPrice(10.0)
                .withAmount(3)
                .build();

        item2 = new Item.Builder()
                .withName("Item2")
                .withDescription("Item2 description")
                .withPrice(15.0)
                .withAmount(7)
                .build();

        item3 = new Item.Builder()
                .withName("Item3")
                .withDescription("Item3 description")
                .withPrice(25.0)
                .withAmount(12)
                .build();
    }

    @Test
    void saveItem() {
        Item savedItem = itemRepository.saveItem(item1);
        assertEquals(item1, savedItem);
        assertThrows(ItemAlreadyExistException.class, () -> itemRepository.saveItem(item1));
    }

    @Test
    void updateItem() {
        itemRepository.saveItem(item1);
        String itemId = item1.getItemId();

        Item updatedItem = new Item.Builder()
                .withName("Updated Item1")
                .withDescription("Updated Item1 description")
                .withPrice(12.0)
                .withAmount(4)
                .build();

        itemRepository.updateItem(updatedItem, itemId);
        assertEquals(updatedItem, itemRepository.getItemById(itemId));

        assertThrows(ItemIdDoesNotExistException.class, () -> itemRepository.updateItem(item2, UUID.randomUUID().toString()));
    }

    @Test
    void getItemById() {
        itemRepository.saveItem(item1);
        assertEquals(item1, itemRepository.getItemById(item1.getItemId()));
        assertThrows(ItemIdDoesNotExistException.class, () -> itemRepository.getItemById(UUID.randomUUID().toString()));
    }

    @Test
    void getStockLow() {
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);

        List<Item> lowStockItems = itemRepository.getStockLow();
        assertEquals(1, lowStockItems.size());
        assertTrue(lowStockItems.contains(item1));
    }

    @Test
    void getStockMedium() {
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);

        List<Item> mediumStockItems = itemRepository.getStockMedium();
        assertEquals(1, mediumStockItems.size());
        assertTrue(mediumStockItems.contains(item2));
    }

    @Test
    void getStockHigh() {
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);

        List<Item> highStockItems = itemRepository.getStockHigh();
        assertEquals(1, highStockItems.size());
        assertTrue(highStockItems.contains(item3));
    }

    @Test
    void getAllItems() {
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);

        List<Item> expectedList = Lists.newArrayList(item1, item2, item3);

        assertEquals(3, this.itemRepository.getAllItems().size());
        assertTrue(this.itemRepository.getAllItems().containsAll(expectedList));

    }
}
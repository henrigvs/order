package com.switchfully.eurder.item.domain;

import com.switchfully.eurder.item.domain.exceptions.IllegalDescriptionFormatException;
import com.switchfully.eurder.item.domain.exceptions.IllegalNameFormatException;
import com.switchfully.eurder.item.domain.exceptions.NegativeAmountException;
import com.switchfully.eurder.item.domain.exceptions.NegativePriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void createItemWithNegativePrice_thenThrowNegativePriceException() {
        assertThrows(NegativePriceException.class, () -> {
            Item item = new Item.Builder()
                    .withName("Test Item")
                    .withDescription("A test item")
                    .withPrice(-1.0)
                    .withAmount(10)
                    .build();
        });
    }

    @Test
    void createItemWithNegativeAmount_thenThrowNegativeAmountException() {
        assertThrows(NegativeAmountException.class, () -> {
            Item item = new Item.Builder()
                    .withName("Test Item")
                    .withDescription("A test item")
                    .withPrice(10.0)
                    .withAmount(-1)
                    .build();
        });
    }

    @Test
    void createItemWithEmptyName_thenThrowIllegalNameFormatException() {
        assertThrows(IllegalNameFormatException.class, () -> {
            Item item = new Item.Builder()
                    .withName("")
                    .withDescription("A test item")
                    .withPrice(10.0)
                    .withAmount(10)
                    .build();
        });
    }

    @Test
    void createItemWithEmptyDescription_thenThrowIllegalDescriptionFormatException() {
        assertThrows(IllegalDescriptionFormatException.class, () -> {
            Item item = new Item.Builder()
                    .withName("Test Item")
                    .withDescription("")
                    .withPrice(10.0)
                    .withAmount(10)
                    .build();
        });
    }

    @Test
    void createItemWithValidValues() {
        Item item = new Item.Builder()
                .withName("Test Item")
                .withDescription("A test item")
                .withPrice(10.0)
                .withAmount(10)
                .build();

        assertNotNull(item.getItemId());
        assertEquals("Test Item", item.getName());
        assertEquals("A test item", item.getDescription());
        assertEquals(10.0, item.getPrice());
        assertEquals(10, item.getAmount());
    }
}

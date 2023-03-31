package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.order.domain.exceptions.IllegalDateOfOrder;
import com.switchfully.eurder.order.domain.exceptions.NegativeAmountOrdered;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemGroupTest {

    @Test
    void createItemGroup_whenNegativeAmountOrdered_thenThrowsNegativeAmountOrdered() {
        String itemId = UUID.randomUUID().toString();
        double price = 10.0;
        int amountOrdered = -1;
        LocalDate shippingDate = LocalDate.now().plusDays(7);

        assertThrows(NegativeAmountOrdered.class, () -> new ItemGroup(itemId, price, amountOrdered, shippingDate));
    }

    @Test
    void createItemGroup_whenShippingDateBeforeNextSevenDays_thenThrowsIllegalDateOfOrder() {
        String itemId = UUID.randomUUID().toString();
        double price = 10.0;
        int amountOrdered = 1;
        LocalDate shippingDate = LocalDate.now().plusDays(6);
        assertThrows(IllegalDateOfOrder.class, () -> new ItemGroup(itemId, price, amountOrdered, shippingDate));
    }

    @Test
    void createItemGroup_whenValidParameters_thenCreatesItemGroup() {
        String itemId = UUID.randomUUID().toString();
        double price = 10.0;
        int amountOrdered = 1;
        LocalDate shippingDate = LocalDate.now().plusDays(7);

        ItemGroup itemGroup = new ItemGroup(itemId, price, amountOrdered, shippingDate);

        assertThat(itemGroup.getItemId()).isEqualTo(itemId);
        assertThat(itemGroup.getAmountOrdered()).isEqualTo(amountOrdered);
        assertThat(itemGroup.getShippingDate()).isEqualTo(shippingDate);
    }
}

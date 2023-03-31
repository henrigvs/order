package com.switchfully.eurder.order.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private final List<ItemGroup> itemGroups = new ArrayList<>();
    private ItemGroup itemGroup1;
    private ItemGroup itemGroup2;

    @BeforeEach
    void setup(){
        itemGroup1 = new ItemGroup(UUID.randomUUID().toString(), 10.0, 2, LocalDate.now().plusDays(7));
        itemGroup2 = new ItemGroup(UUID.randomUUID().toString(), 15, 2, LocalDate.now().plusDays(7));
    }

    @Test
    void createAnOrder(){
        String customerId = UUID.randomUUID().toString();
        itemGroups.add(itemGroup1);
        itemGroups.add(itemGroup2);

        Order order = new Order(customerId, itemGroups);

        assertNotNull(order.getOrderId());
        assertEquals(customerId, order.getCustomerId());
        assertTrue(order.getItemGroups().containsAll(itemGroups));
        assertEquals(10*2+15*2, order.getTotalPrice());
    }
}

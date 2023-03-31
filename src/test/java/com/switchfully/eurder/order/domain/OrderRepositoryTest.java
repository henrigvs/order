package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.order.domain.exceptions.OrderAlreadyExistsException;
import com.switchfully.eurder.order.domain.exceptions.OrderIdDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private Order order;
    private String customerId;

    @BeforeEach
    void setup(){
        orderRepository = new OrderRepository();
        customerId = UUID.randomUUID().toString();
        ItemGroup itemGroup1 = new ItemGroup(UUID.randomUUID().toString(), 10.0, 15, LocalDate.now().plusDays(7));
        ItemGroup itemGroup2 = new ItemGroup(UUID.randomUUID().toString(), 15.0, 3, LocalDate.now().plusDays(7));
        order = new Order(customerId, List.of(itemGroup1, itemGroup2));
    }

    @Test
    void saveOrder(){
        Order savedOrder = this.orderRepository.saveOrder(order);
        assertEquals(savedOrder, order);

        //if order id already exists
        assertThrows(OrderAlreadyExistsException.class, () -> this.orderRepository.saveOrder(order));
    }

    @Test
    void whenAnOrderIsSaved_thenICanRetrieveItWithItsOrderId(){
        this.orderRepository.saveOrder(order);
        Order fetchedOrderById = this.orderRepository.getOrderById(order.getOrderId());
        assertEquals(order, fetchedOrderById);

        //if order id doesn't exist
        assertThrows(OrderIdDoesNotExistException.class, () -> this.orderRepository.getOrderById(UUID.randomUUID().toString()));
    }

    @Test
    void whenAnOrderIsSaved_thenICanRetrieveItWithCustomerId(){
        this.orderRepository.saveOrder(order);
        List<Order> fetchedOrdersByCustomerId = this.orderRepository.getAllOrderOfACustomer(customerId);
        assertEquals(1, fetchedOrdersByCustomerId.size());
        assertEquals(fetchedOrdersByCustomerId.get(0).getCustomerId(), customerId);
    }

    @Test
    void whenAnOrderIsSaved_thenICanRetrieveItWithCustomerId_andByDateRange() {
        this.orderRepository.saveOrder(order);
        System.out.println("Order Repository: " + orderRepository);

        List<Order> fetchedOrdersByCustomerIdAndByDate = this.orderRepository.getAllOrderOfACustomerByDate(customerId, LocalDate.now(), LocalDate.now());
        assertEquals(1, fetchedOrdersByCustomerIdAndByDate.size());
        assertEquals(fetchedOrdersByCustomerIdAndByDate.get(0).getCustomerId(), customerId);
        assertEquals(fetchedOrdersByCustomerIdAndByDate.get(0).getOrderDate(), LocalDate.now());
    }

}

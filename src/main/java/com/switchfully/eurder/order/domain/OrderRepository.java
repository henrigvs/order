package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.order.domain.exceptions.OrderAlreadyExistsException;
import com.switchfully.eurder.order.domain.exceptions.OrderIdDoesNotExistException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OrderRepository {

    private final ConcurrentHashMap<String, Order> orderRepository;

    public OrderRepository() {
        this.orderRepository = new ConcurrentHashMap<>();
    }

    public Order saveOrder(Order order){
        if(!isOrderUnique(order.getOrderId()))
            throw new OrderAlreadyExistsException();
        orderRepository.put(order.getOrderId(), order);
        return order;
    }

    public Order getOrderById(String orderId){
        if(!this.orderRepository.containsKey(orderId))
            throw new OrderIdDoesNotExistException();
        return orderRepository.get(orderId);
    }

    public List<Order> getAllOrderOfACustomer(String customerId){
        return this.orderRepository.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrderOfACustomerByDate(String customerId, LocalDate startDate, LocalDate endDate){
        if(startDate.isEqual(endDate)){
            return this.orderRepository.values().stream()
                    .filter(order -> order.getCustomerId().equals(customerId))
                    .filter(order -> order.getOrderDate().isEqual(startDate))
                    .collect(Collectors.toList());
        }
        else{
            return this.orderRepository.values().stream()
                    .filter(order -> order.getCustomerId().equals(customerId))
                    .filter(order -> !order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }
    }

    public Collection<Order> getAllOrders(){
        return this.orderRepository.values();
    }

    private boolean isOrderUnique(String orderId){
        return !orderRepository.containsKey(orderId);
    }

    @Override
    public String toString() {
        return "OrderRepository{" +
                "orderRepository=" + orderRepository +
                '}';
    }
}

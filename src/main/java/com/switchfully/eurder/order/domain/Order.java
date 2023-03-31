package com.switchfully.eurder.order.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private final String orderId;
    private final String customerId;
    private final List<ItemGroup> itemGroups;
    private final double totalPrice;
    private final LocalDate orderDate;

    public Order(String customerId, List<ItemGroup> itemGroups) {
        this.orderId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroups = itemGroups;
        this.totalPrice = calculateTotalPrice(itemGroups);
        this.orderDate = LocalDate.now();
    }

    public static double calculateTotalPrice(List<ItemGroup> itemGroups){
        return itemGroups.stream()
                .mapToDouble(itemGroup -> itemGroup.getPrice() * itemGroup.getAmountOrdered())
                .sum();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getOrderDate(){
        return this.orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderId, order.orderId) && Objects.equals(customerId, order.customerId) && Objects.equals(itemGroups, order.itemGroups) && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, itemGroups, totalPrice, orderDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", itemGroups=" + itemGroups +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                '}';
    }
}

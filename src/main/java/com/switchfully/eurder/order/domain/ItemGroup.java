package com.switchfully.eurder.order.domain;

import com.switchfully.eurder.order.domain.exceptions.IllegalDateOfOrder;
import com.switchfully.eurder.order.domain.exceptions.NegativeAmountOrdered;

import java.time.LocalDate;
import java.util.Objects;

public class ItemGroup {

    private final String itemId;
    private final double price;
    private final int amountOrdered;
    private final LocalDate shippingDate;

    public ItemGroup(String itemId, double price, int amountOrdered, LocalDate shippingDate) {
        if(amountOrdered < 0)
            throw new NegativeAmountOrdered();
        if(shippingDate.isBefore(LocalDate.now().plusDays(1)))
            throw new IllegalDateOfOrder();
        this.itemId = itemId;
        this.price = price;
        this.amountOrdered = amountOrdered;
        this.shippingDate = shippingDate;
    }

    public String getItemId() {
        return itemId;
    }

    public double getPrice() {
        return price;
    }

    public int getAmountOrdered() {
        return amountOrdered;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemGroup itemGroup)) return false;
        return Double.compare(itemGroup.price, price) == 0 && amountOrdered == itemGroup.amountOrdered && Objects.equals(itemId, itemGroup.itemId) && Objects.equals(shippingDate, itemGroup.shippingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, price, amountOrdered, shippingDate);
    }

    @Override
    public String toString() {
        return "ItemGroup{" +
                "itemId='" + itemId + '\'' +
                ", price=" + price +
                ", amountOrdered=" + amountOrdered +
                ", shippingDate=" + shippingDate +
                '}';
    }
}

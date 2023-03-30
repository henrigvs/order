package com.switchfully.eurder.item.domain;

import com.switchfully.eurder.item.domain.exceptions.IllegalDescriptionFormatException;
import com.switchfully.eurder.item.domain.exceptions.IllegalNameFormatException;
import com.switchfully.eurder.item.domain.exceptions.NegativeAmountException;
import com.switchfully.eurder.item.domain.exceptions.NegativePriceException;

import java.util.Objects;
import java.util.UUID;

public class Item {

    private final String itemId;
    private String name;
    private String description;
    private double price;
    private int amount;

    private Item(String name, String description, double price, int amount) {
        if (name == null || name.isEmpty()) {
            throw new IllegalNameFormatException();
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalDescriptionFormatException();
        }
        if (price < 0) {
            throw new NegativePriceException();
        }
        if (amount < 0) {
            throw new NegativeAmountException();
        }

        this.itemId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Double.compare(item.price, price) == 0 && amount == item.amount && Objects.equals(name, item.name) && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, amount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public static class Builder{

        private String itemId;
        private String name;
        private String description;
        private double price;
        private int amount;

        public Item build(){
            return new Item(name, description, price, amount);
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withDescription (String description){
            this.description = description;
            return this;
        }

        public Builder withPrice (double price){
            this.price = price;
            return this;
        }

        public Builder withAmount (int amount){
            this.amount = amount;
            return this;
        }
    }
}

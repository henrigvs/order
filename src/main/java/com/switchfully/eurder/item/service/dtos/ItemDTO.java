package com.switchfully.eurder.item.service.dtos;

import java.util.Objects;

public class ItemDTO {

    private String itemId;
    private String name;
    private String description;
    private double price;
    private int amount;

    public String getItemId() {
        return itemId;
    }

    public ItemDTO setId(String itemId){
        this.itemId = itemId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemDTO setName(String name){
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ItemDTO setDescription(String description){
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ItemDTO setPrice(double price){
        this.price = price;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemDTO setAmount(int amount){
        this.amount = amount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDTO itemDTO)) return false;
        return Double.compare(itemDTO.price, price) == 0 && amount == itemDTO.amount && Objects.equals(itemId, itemDTO.itemId) && Objects.equals(name, itemDTO.name) && Objects.equals(description, itemDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, description, price, amount);
    }
}

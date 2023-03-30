package com.switchfully.eurder.item.domain;

import com.switchfully.eurder.item.domain.exceptions.ItemAlreadyExistException;
import com.switchfully.eurder.item.domain.exceptions.ItemIdDoesNotExistException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {

    private final ConcurrentHashMap<String, Item> itemRepository;

    public ItemRepository() {
        this.itemRepository = new ConcurrentHashMap<>();
    }

    public Item saveItem(Item item) {
        if (itemRepository.containsKey(item.getItemId())) {
            throw new ItemAlreadyExistException(item.getItemId());
        } else {
            itemRepository.put(item.getItemId(), item);
            return item;
        }
    }

    public Item updateItem(Item updateItem, String itemId) {
        if (itemRepository.containsKey(itemId)) {
            itemRepository.put(itemId, updateItem);
        } else {
            throw new ItemIdDoesNotExistException(updateItem.getItemId());
        }
        return updateItem;
    }

    public Item getItemById(String itemId) {
        Item item = itemRepository.get(itemId);
        if (item == null) {
            throw new ItemIdDoesNotExistException(itemId);
        } else {
            return item;
        }
    }

    public List<Item> getStockLow() {
        return itemRepository.values().stream()
                .filter(item -> item.getAmount() < 5)
                .collect(Collectors.toList());
    }

    public List<Item> getStockMedium() {
        return itemRepository.values().stream()
                .filter(item -> item.getAmount() >= 5 && item.getAmount() < 10)
                .collect(Collectors.toList());
    }

    public List<Item> getStockHigh() {
        return itemRepository.values().stream()
                .filter(item -> item.getAmount() >= 10)
                .collect(Collectors.toList());
    }

    public Collection<Item> getAllItems(){
        return this.itemRepository.values();
    }
}

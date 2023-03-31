package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.CreateItemDTO;
import com.switchfully.eurder.item.service.dtos.ItemDTO;
import com.switchfully.eurder.item.service.dtos.UpdateItemDTO;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private ItemService itemService;
    private CreateItemDTO createItemDTO;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepository();
        userRepository = new UserRepository();
        itemService = new ItemService(itemRepository, userRepository);
        item1 = new Item.Builder()
                .withName("CatRoll")
                .withDescription("To remove cat hair")
                .withPrice(0.99)
                .withAmount(1500)
                .build();
        item2 = new Item.Builder()
                .withName("Hair wax")
                .withDescription("An hard styling")
                .withPrice(3.99)
                .withAmount(8)
                .build();
        item3 = new Item.Builder()
                .withName("Colgate")
                .withDescription("A white smile")
                .withPrice(1.49)
                .withAmount(1)
                .build();
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        itemRepository.saveItem(item3);

        createItemDTO = new CreateItemDTO("Item 1", "Item 1 description", 15, 200);
        User admin = new User("AdminId", "Admin", "Admin", "admin@admin.admin", "password", new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles"), new PhoneNumber("+32", "476845621"), Role.ADMINISTRATOR);
        userRepository.saveUser(admin);
    }

    @Test
    void saveItem() {
        String adminId = "AdminId";
        ItemDTO savedItemDTO = itemService.saveItem(createItemDTO, adminId);

        assertNotNull(savedItemDTO);
        assertEquals(createItemDTO.name(), savedItemDTO.getName());
        assertEquals(createItemDTO.description(), savedItemDTO.getDescription());
        assertEquals(createItemDTO.price(), savedItemDTO.getPrice());
        assertEquals(createItemDTO.amount(), savedItemDTO.getAmount());
    }
    @Test
    void updateItem() {
        String adminId = "AdminId";
        UpdateItemDTO updateItemDTO = new UpdateItemDTO("Update Item", "Updated Item Description", 25.99, 100);
        Item item = new Item.Builder()
                .withName("Item 1")
                .withDescription("Item 1 description")
                .withPrice(10.5)
                .withAmount(100)
                .build();

        itemRepository.saveItem(item);

        ItemDTO updatedItemDTO = itemService.updateItem(updateItemDTO, item.getItemId(), adminId);

        assertNotNull(updatedItemDTO);
        assertEquals(updateItemDTO.name(), updatedItemDTO.getName());
        assertEquals(updateItemDTO.description(), updatedItemDTO.getDescription());
        assertEquals(updateItemDTO.price(), updatedItemDTO.getPrice());
        assertEquals(updateItemDTO.amount(), updatedItemDTO.getAmount());
    }

    @Test
    void getItemById() {
        Item item = new Item.Builder()
                .withName("Item 1")
                .withDescription("Item 1 description")
                .withPrice(10.5)
                .withAmount(100)
                .build();
        String itemId = item.getItemId();
        itemRepository.saveItem(item);

        ItemDTO itemDTO = itemService.getItemById(itemId);

        assertEquals(itemDTO.getItemId(), item.getItemId());
        assertEquals(item.getName(), itemDTO.getName());
        assertEquals(item.getDescription(), itemDTO.getDescription());
        assertEquals(item.getPrice(), itemDTO.getPrice());
        assertEquals(item.getAmount(), itemDTO.getAmount());
    }

    @Test
    void getStockLow(){
        List<ItemDTO> lowStockItems = itemService.getStockLow("AdminId");

        assertNotNull(lowStockItems);
        assertEquals(1, lowStockItems.size());

        ItemDTO lowStockItem = lowStockItems.get(0);

        assertEquals(item3.getName(), lowStockItem.getName());
        assertEquals(item3.getDescription(), lowStockItem.getDescription());
        assertEquals(item3.getPrice(), lowStockItem.getPrice());
        assertEquals(item3.getAmount(), lowStockItem.getAmount());
    }

    @Test
    void getStockMedium(){
        List<ItemDTO> mediumStockItems = itemService.getStockMedium("AdminId");

        assertNotNull(mediumStockItems);
        assertEquals(1, mediumStockItems.size());

        ItemDTO mediumStockItem = mediumStockItems.get(0);

        assertEquals(item2.getName(), mediumStockItem.getName());
        assertEquals(item2.getDescription(), mediumStockItem.getDescription());
        assertEquals(item2.getPrice(), mediumStockItem.getPrice());
        assertEquals(item2.getAmount(), mediumStockItem.getAmount());
    }

    @Test
    void getStockHigh(){
        List<ItemDTO> highStockItems = itemService.getStockHigh("AdminId");

        assertNotNull(highStockItems);
        assertEquals(1, highStockItems.size());

        ItemDTO highStockItem = highStockItems.get(0);

        assertEquals(item1.getName(), highStockItem.getName());
        assertEquals(item1.getDescription(), highStockItem.getDescription());
        assertEquals(item1.getPrice(), highStockItem.getPrice());
        assertEquals(item1.getAmount(), highStockItem.getAmount());
    }
}
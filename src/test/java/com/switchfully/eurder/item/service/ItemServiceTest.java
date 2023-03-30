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

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private ItemService itemService;
    private CreateItemDTO createItemDTO;

    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepository();
        userRepository = new UserRepository();
        itemService = new ItemService(itemRepository, userRepository);

        createItemDTO = new CreateItemDTO("Item 1", "Item 1 description", 15, 200);
        User admin = new User("AdminId", "Admin", "Admin", "admin@admin.admin", "password", new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles"), new PhoneNumber("+32", "476845621"), Role.ADMINISTRATOR);
        userRepository.saveUser(admin);
    }

    @Test
    void saveItem() {
        ItemDTO savedItemDTO = itemService.saveItem(createItemDTO);

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
}
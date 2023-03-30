package com.switchfully.eurder.item.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.item.service.dtos.CreateItemDTO;
import com.switchfully.eurder.item.service.dtos.ItemDTO;
import com.switchfully.eurder.item.service.dtos.UpdateItemDTO;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.service.exceptions.InsufficientAccessRightException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = new ItemMapper();
    }

    public ItemDTO saveItem(CreateItemDTO createItemDTO){
        Item item = itemRepository.saveItem(itemMapper.toEntity(createItemDTO));

        return itemMapper.toDTO(item);
    }

    public ItemDTO updateItem(UpdateItemDTO updatingItemDTO, String itemId, String adminId){
        User admin = userRepository.getUserByUserId(adminId);
        validateAdminAccess(admin);

        Item itemToUpdate = itemRepository.getItemById(itemId);
        itemToUpdate.setName(updatingItemDTO.name());
        itemToUpdate.setDescription(updatingItemDTO.description());
        itemToUpdate.setPrice(updatingItemDTO.price());
        itemToUpdate.setAmount(updatingItemDTO.amount());

        Item updatedItem = itemRepository.updateItem(itemToUpdate, itemId);
        return itemMapper.toDTO(updatedItem);
    }

    public ItemDTO getItemById(String itemId){
        return itemMapper.toDTO(itemRepository.getItemById(itemId));
    }

    public List<ItemDTO> getStockLow(String adminId){
        validateAdminAccess(userRepository.getUserByUserId(adminId));
        return itemMapper.toDTOList(itemRepository.getStockLow());
    }

    public List<ItemDTO> getStockMedium(String adminId){
        validateAdminAccess(userRepository.getUserByUserId(adminId));
        return itemMapper.toDTOList(itemRepository.getStockMedium());
    }

    public List<ItemDTO> getStockHigh(String adminId){
        validateAdminAccess(userRepository.getUserByUserId(adminId));
        return itemMapper.toDTOList(itemRepository.getStockHigh());
    }

    public List<ItemDTO> getAllItems(String adminId){
        validateAdminAccess(userRepository.getUserByUserId(adminId));
        return itemRepository.getAllItems().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateAdminAccess(User adminUser) {
        if (adminUser == null || !adminUser.getRole().equals(Role.ADMINISTRATOR)) {
            throw new InsufficientAccessRightException();
        }
    }
}

package com.switchfully.eurder.order.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.order.domain.ItemGroup;
import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.domain.exceptions.NonExistingOrderException;
import com.switchfully.eurder.order.service.dtos.itemgroupdtos.*;
import com.switchfully.eurder.order.service.dtos.orderdtos.*;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.exceptions.NonExistingUserIdException;
import com.switchfully.eurder.user.service.exceptions.InsufficientAccessRightException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = new OrderMapper();
    }

    public OrderDTO createNewOrder(String userId, List<CreateItemGroupDTO> createItemGroupDTOS) {
        validateCustomer(userId);
        List<ItemGroupDTO> itemGroupDTOs = validateItemGroupList(createItemGroupDTOS);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(userId, itemGroupDTOs);

        return orderMapper.toDTO(this.orderRepository.saveOrder(orderMapper.toEntity(createOrderDTO)));
    }

    public OrderDTO reorderAnExistingOrder(String orderId) {
        validateOrder(orderId);
        Order order = orderRepository.getOrderById(orderId);
        String userId = order.getCustomerId();

        List<CreateItemGroupDTO> createItemGroupDTOList = new ArrayList<>();
        for (ItemGroup itemGroup : order.getItemGroups()) {
            createItemGroupDTOList.add(new CreateItemGroupDTO(itemGroup.getItemId(), itemGroup.getAmountOrdered()));
        }

        List<ItemGroupDTO> itemGroupDTOs = validateItemGroupList(createItemGroupDTOList);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO(userId, itemGroupDTOs);
        return orderMapper.toDTO(orderRepository.saveOrder(orderMapper.toEntity(createOrderDTO)));
    }

    public List<OrderDTO> getAllOrders(String adminId) {
        validateAdmin(adminId);

        return orderRepository.getAllOrders().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderReportDTO getMyReportAsCustomer(String customerId) {
        validateCustomer(customerId);

        List<OrderDTO> allOrdersByCustomer = orderMapper.toDTOList(orderRepository.getAllOrderOfACustomer(customerId));
        double total = allOrdersByCustomer.stream()
                .mapToDouble(OrderDTO::totalPrice)
                .sum();
        return new OrderReportDTO(allOrdersByCustomer, total);
    }

    /* ---------------- private methods ---------------- */

    private void validateOrder(String orderId) {
        if (orderRepository.getOrderById(orderId) == null)
            throw new NonExistingOrderException();
    }

    private void validateCustomer(String userId) {
        if (userRepository.getUserByUserId(userId) == null)
            throw new NonExistingUserIdException();
    }

    private void validateAdmin(String adminId) {
        if (!userRepository.getUserByUserId(adminId).getRole().equals(Role.ADMINISTRATOR))
            throw new InsufficientAccessRightException();
    }

    private List<ItemGroupDTO> validateItemGroupList(List<CreateItemGroupDTO> createItemGroupDTOList) {
        return createItemGroupDTOList.stream()
                .map(this::createItemGroupDTO)
                .collect(Collectors.toList());
    }

    private ItemGroupDTO createItemGroupDTO(CreateItemGroupDTO createItemGroupDTO) {
        String itemId = createItemGroupDTO.itemId();
        Item item = itemRepository.getItemById(itemId);
        double price = item.getPrice();
        int amountOrdered = createItemGroupDTO.amountOrdered();
        int remainingAmountInStock = item.getAmount();
        LocalDate shippingDate = calculateShippingDate(amountOrdered, remainingAmountInStock);
        updateItemAmount(item, amountOrdered, remainingAmountInStock);
        return new ItemGroupDTO(itemId, price, amountOrdered, shippingDate);
    }

    private LocalDate calculateShippingDate(int amountOrdered, int remainingAmountInStock) {
        return remainingAmountInStock < amountOrdered ? LocalDate.now().plusDays(7) : LocalDate.now().plusDays(1);
    }

    private void updateItemAmount(Item item, int amountOrdered, int remainingAmountInStock) {
        int updatedAmount = remainingAmountInStock < amountOrdered ? 0 : remainingAmountInStock - amountOrdered;
        item.setAmount(updatedAmount);
    }
}

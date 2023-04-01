package com.switchfully.eurder.order.service;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.order.domain.OrderRepository;
import com.switchfully.eurder.order.service.dtos.itemgroupdtos.CreateItemGroupDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderReportDTO;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;
    private String item1Id;
    private String item2Id;

    @BeforeEach
    void setUp() {
        OrderRepository orderRepository = new OrderRepository();
        UserRepository userRepository = new UserRepository();
        ItemRepository itemRepository = new ItemRepository();

        User admin = new User("AdminId", "Admin", "Admin", "admin@admin.admin", "password",
                new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles"),
                new PhoneNumber("+32", "476845621"),
                Role.ADMINISTRATOR);
        User customer = new User("CR7", "Cristiano", "Ronaldo", "cr7@juventus.it", "cr7",
                new Address("Via Druento", "175", null, "10151", "Torino"),
                new PhoneNumber("+39", "114530486"),
                Role.CUSTOMER);
        userRepository.saveUser(admin);
        userRepository.saveUser(customer);

        Item item1 = new Item.Builder()
                .withName("item1")
                .withDescription("Item1 description")
                .withPrice(15)
                .withAmount(5)
                .build();
        Item item2 = new Item.Builder()
                .withName("item2")
                .withDescription("Item2 description")
                .withPrice(10)
                .withAmount(20)
                .build();
        itemRepository.saveItem(item1);
        itemRepository.saveItem(item2);
        item1Id = item1.getItemId();
        item2Id = item2.getItemId();

        orderService = new OrderService(orderRepository, userRepository, itemRepository);
    }

    @Test
    void createNewOrder() {
        List<CreateItemGroupDTO> itemGroupDTOList = List.of(
                new CreateItemGroupDTO(item1Id, 2),
                new CreateItemGroupDTO(item2Id, 5));

        OrderDTO orderDTO = orderService.createNewOrder("CR7", itemGroupDTOList);

        assertNotNull(orderDTO.orderId());
        assertEquals(orderDTO.orderDate(), LocalDate.now());
        assertEquals(orderDTO.customerId(), "CR7");
        assertEquals(orderDTO.totalPrice(), 15 * 2 + 10 * 5);
    }

    @Test
    void reorderAnExistingOrder() {
        List<CreateItemGroupDTO> itemGroupDTOList = List.of(
                new CreateItemGroupDTO(item1Id, 2),
                new CreateItemGroupDTO(item2Id, 5));
        OrderDTO orderDTO = orderService.createNewOrder("CR7", itemGroupDTOList);
        String orderId = orderDTO.orderId();

        OrderDTO reOrder = orderService.reorderAnExistingOrder(orderId);

        assertNotNull(reOrder.orderId());
        assertEquals(orderDTO.customerId(), reOrder.customerId());
        assertEquals(orderDTO.itemGroupDTOList(), reOrder.itemGroupDTOList());
        assertEquals(orderDTO.totalPrice(), reOrder.totalPrice());
    }

    @Test
    void getAllOrders() {
        List<CreateItemGroupDTO> itemGroupDTOList = List.of(
                new CreateItemGroupDTO(item1Id, 2),
                new CreateItemGroupDTO(item2Id, 5));
        OrderDTO orderDTO = orderService.createNewOrder("CR7", itemGroupDTOList);

        List<OrderDTO> allOrders = orderService.getAllOrders("AdminId");

        assertEquals(1, allOrders.size());
        assertTrue(allOrders.contains(orderDTO));
    }

    @Test
    void getMyReportAsCustomer() {
        List<CreateItemGroupDTO> itemGroupDTOList = List.of(
                new CreateItemGroupDTO(item1Id, 2),
                new CreateItemGroupDTO(item2Id, 5));
        OrderDTO orderDTO = orderService.createNewOrder("CR7", itemGroupDTOList);

        OrderReportDTO orderReportDTO = orderService.getMyReportAsCustomer("CR7");
        assertNotNull(orderReportDTO);
        assertEquals(1, orderReportDTO.allOrders().size());
        assertTrue(orderReportDTO.allOrders().contains(orderDTO));
    }
}


package com.switchfully.eurder.order.api;

import com.switchfully.eurder.item.domain.Item;
import com.switchfully.eurder.item.domain.ItemRepository;
import com.switchfully.eurder.order.service.OrderService;
import com.switchfully.eurder.order.service.dtos.itemgroupdtos.CreateItemGroupDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderReportDTO;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;

    private String itemId_1;
    private String itemId_2;

    @BeforeEach
    void setup(){
        Item item_1 = new Item.Builder()
                .withName("Iphone 14 Pro Max")
                .withDescription("Latest generation of smartphone design")
                .withPrice(1299.95)
                .withAmount(1)
                .build();
        Item item_2 = new Item.Builder()
                .withName("Samsung S23")
                .withDescription("The best photo resolution")
                .withPrice(1095.00)
                .withAmount(1)
                .build();
        itemRepository.saveItem(item_1);
        itemRepository.saveItem(item_2);
        itemId_1 = item_1.getItemId();
        itemId_2 = item_2.getItemId();
        User cr7 = new User("CR7", "Cristiano", "Ronaldo", "cr7@juventus.it", "password"
                , new Address("Via Druento", "175", null, "10151", "Torino")
                , new PhoneNumber("+39", "114530486")
                , Role.CUSTOMER);
        userRepository.saveUser(cr7);
    }

    @Test
    void createNewOrder(){
        // Given
        String JSON_payload = String.format(
        """
        [
        {
            "itemId": "%s",
            "amountOrdered": %d
        },
        {
            "itemId": "%s",
            "amountOrdered": %d
        }
        ]
        """, itemId_1, 1, itemId_2, 1);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(JSON_payload)

        // When
                        .when()
                        .port(port)
                        .post("/orders/order/CR7")

        // Then
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        OrderDTO orderDTO = response.jsonPath().getObject("", OrderDTO.class);
        assertNotNull(orderDTO.orderId());
        assertEquals(orderDTO.customerId(), "CR7");
        assertEquals(orderDTO.orderDate(), LocalDate.now());
        assertEquals(orderDTO.itemGroupDTOList().size(), 2);
        assertEquals(orderDTO.itemGroupDTOList().get(0).itemId(), itemId_1);
        assertEquals(orderDTO.itemGroupDTOList().get(0).amountOrdered(), 1);
        assertEquals(orderDTO.itemGroupDTOList().get(0).shippingDate(), LocalDate.now().plusDays(1));
        assertEquals(orderDTO.itemGroupDTOList().get(1).itemId(), itemId_2);
        assertEquals(orderDTO.itemGroupDTOList().get(1).amountOrdered(), 1);
        assertEquals(orderDTO.itemGroupDTOList().get(1).shippingDate(), LocalDate.now().plusDays(1));
    }

    @Test
    void reorderAnExistingOrder(){
        // Given
        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO(itemId_1, 1);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO(itemId_2, 1);
        List<CreateItemGroupDTO> createItemGroupDTOList = List.of(createItemGroupDTO1, createItemGroupDTO2);

        OrderDTO initialOrderDTO = orderService.createNewOrder("CR7", createItemGroupDTOList);
        String orderId = initialOrderDTO.orderId();

        Response response =
                given()
                        .contentType(ContentType.JSON)
        // When
                        .when()
                        .port(port)
                        .post("/orders/reorder/" + orderId)
        // Then
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        OrderDTO newOrder = response.jsonPath().getObject("", OrderDTO.class);
        assertNotNull(newOrder.orderId());
        assertEquals(newOrder.customerId(), "CR7");
        assertEquals(newOrder.orderDate(), LocalDate.now());
        assertEquals(newOrder.itemGroupDTOList().size(), 2);
        assertEquals(newOrder.itemGroupDTOList().get(0).itemId(), initialOrderDTO.itemGroupDTOList().get(0).itemId());
        assertEquals(newOrder.itemGroupDTOList().get(0).amountOrdered(), initialOrderDTO.itemGroupDTOList().get(0).amountOrdered());
        assertEquals(newOrder.itemGroupDTOList().get(0).shippingDate(), LocalDate.now().plusDays(7));
        assertEquals(newOrder.itemGroupDTOList().get(1).itemId(), initialOrderDTO.itemGroupDTOList().get(1).itemId());
        assertEquals(newOrder.itemGroupDTOList().get(1).amountOrdered(), initialOrderDTO.itemGroupDTOList().get(1).amountOrdered());
        assertEquals(newOrder.itemGroupDTOList().get(1).shippingDate(), LocalDate.now().plusDays(7));
    }

    @Test
    void getAllOrders(){
        // Given
        String adminId = "initialAdmin";
        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO(itemId_1, 1);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO(itemId_2, 1);
        List<CreateItemGroupDTO> createItemGroupDTOList = List.of(createItemGroupDTO1, createItemGroupDTO2);
        orderService.createNewOrder("CR7", createItemGroupDTOList);
        List<OrderDTO> expectedList = orderService.getAllOrders(adminId);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .header("adminId", adminId)
        // When
                        .when()
                        .port(port)
                        .get("/orders/allOrders")
        // Then
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        List<OrderDTO> allOrders = response.jsonPath().getList("", OrderDTO.class);
        assertTrue(allOrders.containsAll(expectedList));
    }

    @Test
    void getMyReportAsCustomer(){
        // Given
        CreateItemGroupDTO createItemGroupDTO1 = new CreateItemGroupDTO(itemId_1, 1);
        CreateItemGroupDTO createItemGroupDTO2 = new CreateItemGroupDTO(itemId_2, 1);
        List<CreateItemGroupDTO> createItemGroupDTOList = List.of(createItemGroupDTO1, createItemGroupDTO2);
        orderService.createNewOrder("CR7", createItemGroupDTOList);
        OrderReportDTO expectedReport = orderService.getMyReportAsCustomer("CR7");

        Response response =
                given()
                        .contentType(ContentType.JSON)
        // When
                        .when()
                        .port(port)
                        .get("/orders/allOrders/CR7")
        // Then
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();

        OrderReportDTO allOrdersForACustomer = response.jsonPath().getObject("", OrderReportDTO.class);
        assertEquals(expectedReport, allOrdersForACustomer);
    }
}

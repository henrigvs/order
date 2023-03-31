package com.switchfully.eurder.item.api;

import com.switchfully.eurder.item.service.ItemService;
import com.switchfully.eurder.item.service.dtos.CreateItemDTO;
import com.switchfully.eurder.item.service.dtos.ItemDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemService itemService;
    private CreateItemDTO createItemDTO;
    private CreateItemDTO createItemDTO2;
    private CreateItemDTO createItemDTO3;

    @BeforeEach
    void setup(){
        createItemDTO = new CreateItemDTO("Colgate", "For a white smile", 2.49, 15);
        createItemDTO2 = new CreateItemDTO("Sensodine", "For strong teeth", 3.49, 7);
        createItemDTO3 = new CreateItemDTO("Elmex", "For protected teeth", 5.59, 3);
    }

    @Test
    void saveItem_whenIProvideA_Correct_JsonPayload_thenTheNewItemIsRegistered(){
        //Given
        String JSON_payload =
                """
                {
                    "name": "Bic",
                    "description": "A good tool to write something down",
                    "price": 0.99,
                    "amount": 1000
                }
                """;
        String adminId = "initialAdmin";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(JSON_payload)
        // When
                .when()
                .header("adminId", adminId)
                .port(port)
                .post("items/item")
        //Then
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Bic"))
                .body("description", equalTo("A good tool to write something down"))
                .body("price", equalTo((float)0.99))
                .body("amount", equalTo(1000))
                .extract()
                .response();

        ItemDTO itemDTO = response.jsonPath().getObject("", ItemDTO.class);
        assertNotNull(itemDTO.getItemId());
    }

    @Test
    void updateItem_whenIProvideACorrectJSONPayload_thenAnExistingItemIsUpdated(){
        // Given
        String adminId = "initialAdmin";
        itemService.saveItem(createItemDTO, adminId);
        List<ItemDTO> listWithItem = itemService.getAllItems(adminId);
        String itemId = listWithItem.get(0).getItemId();

        String JSON_payload =
                """
                {
                    "name": "Colgate",
                    "description": "For a white smile",
                    "price": 1.99,
                    "amount": 15
                }
                """;    //Price modified 2.49 => 1.99

        Response response=
                given()
                        .contentType(ContentType.JSON)
                        // When
                        .when()
                        .body(JSON_payload)
                        .header("adminId", adminId)
                        .port(port)
                        .put("/items/item/" + itemId)
                        // Then
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .response();

        ItemDTO expectedItem = response.jsonPath().getObject("", ItemDTO.class);
        assertEquals(1.99, expectedItem.getPrice());
    }

    @Test
    void getAllItems(){
        // Given
        String adminId = "initialAdmin";
        itemService.saveItem(createItemDTO, adminId);
        itemService.saveItem(createItemDTO2, adminId);
        List<ItemDTO> itemDTOListExpected = itemService.getAllItems(adminId);

        Response response=
                given()
                .contentType(ContentType.JSON)
        // When
                .when()
                .header("adminId", adminId)
                .port(port)
                .get("/items/allItems")
        // Then
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        List<ItemDTO> itemDTOList = response.jsonPath().getList("", ItemDTO.class);
        assertTrue(itemDTOList.containsAll(itemDTOListExpected));
    }

    // We only test medium, low and high are the same implementation
    @Test
    void getStockMedium(){
        // Given
        String adminId = "initialAdmin";
        itemService.saveItem(createItemDTO, adminId);
        itemService.saveItem(createItemDTO2, adminId);
        itemService.saveItem(createItemDTO3, adminId);
        List<ItemDTO> itemDTOListExpected = itemService.getStockMedium(adminId);

        Response response=
                given()
                        .contentType(ContentType.JSON)
                        // When
                        .when()
                        .header("adminId", adminId)
                        .port(port)
                        .get("/items/mediumStock")
                        // Then
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .response();

        List<ItemDTO> itemDTOList = response.jsonPath().getList("", ItemDTO.class);
        assertTrue(itemDTOList.containsAll(itemDTOListExpected));
    }
}

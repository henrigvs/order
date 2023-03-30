package com.switchfully.eurder.user.api;

import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.user.service.UserMapper;
import com.switchfully.eurder.user.service.UserService;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDTO;
import com.switchfully.eurder.user.service.dtos.UserDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    private CreateCustomerDTO createCustomerDTO;
    private UserMapper userMapper;

    @BeforeEach
    void setup(){
        createCustomerDTO = new CreateCustomerDTO("henriTheJavaDev", "Henri", "Gevenois", "henri.gevenois@proton.com", "1234"
                , new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles")
                , new PhoneNumber("+32", "476845621"));
        userMapper = new UserMapper();
    }

    @Test
    void registerCustomer_whenIProvideA_Correct_JsonPayload_thenTheNewCustomerIsRegistered(){
        String JSON_payload =
                """
                {
                    "userId": "henriJavaDev",
                    "firstName": "Henri",
                    "lastName": "Gevenois",
                    "email": "henri.gevenois@proton.org",
                    "password": "1234",
                    "address": {
                        "street": "Rue de l'Aire",
                        "number": "102",
                        "box": null,
                        "zip": "7060",
                        "city": "Horrues"
                    },
                    "phoneNumber": {
                        "countryCode": "+32",
                        "localNumber": "476983887"
                    }
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(JSON_payload)
                .when()
                .port(port)
                .post("users/customer")

                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("userId", equalTo("henriJavaDev"))
                .body("firstName", equalTo("Henri"))
                .body("lastName", equalTo("Gevenois"))
                .body("email", equalTo("henri.gevenois@proton.org"))
                .extract()
                .response();
    }

    @Test
    void registerCustomer_whenIProvideAn_Incorrect_JsonPayload_thenTheNewCustomerIsRegistered(){
        String JSON_payload =   //email is empty
                        """
                        {
                            "userId": "henriJavaDev",
                            "firstName": "Henri",
                            "lastName": "Gevenois",
                            "email": "",
                            "password": "1234",
                            "address": {
                                "street": "Rue de l'Aire",
                                "number": "102",
                                "box": null,
                                "zip": "7060",
                                "city": "Horrues"
                            },
                            "phoneNumber": {
                                "countryCode": "+32",
                                "localNumber": "476983887"
                            }
                        }
                        """;

        given()
                .contentType(ContentType.JSON)
                .body(JSON_payload)
                .when()
                .port(port)
                .post("users/customer")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Invalid email format"));

    }

    @Test
    void registerAdmin_whenIProvideAn_Correct_JsonPayload_andAnAdminID_thenTheNewAdminIsRegistered(){

        String JSON_payload =
                """
                {
                    "userId": "henriJavaDev",
                    "firstName": "Henri",
                    "lastName": "Gevenois",
                    "email": "henri.gevenois@proton.org",
                    "password": "1234",
                    "address": {
                        "street": "Rue de l'Aire",
                        "number": "102",
                        "box": null,
                        "zip": "7060",
                        "city": "Horrues"
                    },
                    "phoneNumber": {
                        "countryCode": "+32",
                        "localNumber": "476983887"
                    }
                }
                """;

        //AdminID required !
        String adminId = "initialAdmin";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(JSON_payload)
                .header("adminId", adminId)
                .when()
                .port(port)
                .post("users/admin")

                .then()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("userId", equalTo("henriJavaDev"))
                .body("firstName", equalTo("Henri"))
                .body("lastName", equalTo("Gevenois"))
                .body("email", equalTo("henri.gevenois@proton.org"))
                .extract()
                .response();
    }

    @Test
    void registerAdmin_whenIProvideAn_Correct_JsonPayload_withoutAdminID_thenTheNewAdminIsRegistered(){

        String JSON_payload =
                """
                {
                    "userId": "henriJavaDev",
                    "firstName": "Henri",
                    "lastName": "Gevenois",
                    "email": "henri.gevenois@proton.org",
                    "password": "1234",
                    "address": {
                        "street": "Rue de l'Aire",
                        "number": "102",
                        "box": null,
                        "zip": "7060",
                        "city": "Horrues"
                    },
                    "phoneNumber": {
                        "countryCode": "+32",
                        "localNumber": "476983887"
                    }
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(JSON_payload)
                .when()
                .port(port)
                .post("users/admin")
                .then()
                .assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }

    @Test
    void whenThereIsOneUserIntoRepository_thenICanRetrieveItByUserId(){
        // Given
        userService.registerCustomer(createCustomerDTO);

        // When
        UserDTO userDTO =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .port(port)
                        .get(String.format("/users/user/%s", createCustomerDTO.getUserId()))
        // Then
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(UserDTO.class);

        assertEquals(createCustomerDTO.getUserId(), userDTO.getUserId());
    }

    @Test
    void whenThereIsOneUserIntoRepository_thenICanRetrieveItByEmailAndByPassword(){
        // Given
        userService.registerCustomer(createCustomerDTO);

        // When
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .header("email", createCustomerDTO.getEmail())
                        .header("password", createCustomerDTO.getPassword())
                        .when()
                        .port(port)
                        .get("/users/login")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .body("user.userId", equalTo("henriTheJavaDev"))
                        .body("message", equalTo("user found"))
                        .extract()
                        .response();

        UserDTO userDTO = response.jsonPath().getObject("user", UserDTO.class);

        assertEquals(createCustomerDTO.getUserId(), userDTO.getUserId());
    }

    @Test
    void whereThereIsSomeUsersIntoRepo_thenICanRetrieveTheList() {
        // Given
        CreateCustomerDTO createCustomerDTO2 = new CreateCustomerDTO("CR7", "Cristiano", "Ronaldo", "cr7@juventus.it", "cr7"
                , new Address("Via Druento", "175", null, "10151", "Torino")
                , new PhoneNumber("+39", "114530486"));

        List<UserDTO> userDTOListExpected = new ArrayList<>();
        userDTOListExpected.add(userMapper.toDTO(userMapper.toEntity(createCustomerDTO)));
        userDTOListExpected.add(userMapper.toDTO(userMapper.toEntity(createCustomerDTO2)));

        userService.registerCustomer(createCustomerDTO);
        userService.registerCustomer(createCustomerDTO2);
        String adminId = "initialAdmin";

        // When
        List<UserDTO> userDTOList =
                given()
                        .contentType(ContentType.JSON)
                        .header("adminId", adminId)
                        .when()
                        .port(port)
                        .get("/users/allUsers")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .body()
                        .jsonPath()
                        .getList("", UserDTO.class);

        assertTrue(userDTOList.containsAll(userDTOListExpected));
    }
}

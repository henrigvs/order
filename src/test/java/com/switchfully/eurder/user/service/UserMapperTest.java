package com.switchfully.eurder.user.service;

import com.google.common.collect.Lists;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.address.exception.InvalidAddressFormatException;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.user.domain.phonenumber.exception.InvalidPhoneNumberFormatException;
import com.switchfully.eurder.user.service.dtos.CreateAdministratorDTO;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDTO;
import com.switchfully.eurder.user.service.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;
    private User user;
    private User user2;

    @BeforeEach
    void setUp() throws InvalidAddressFormatException, InvalidPhoneNumberFormatException, InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        userMapper = new UserMapper();
        user = new User("henriTheJavaDev", "Henri", "Gevenois", "henri.gevenois@proton.com", "1234"
                , new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles")
                , new PhoneNumber("+32", "476845621")
                , Role.ADMINISTRATOR);

        user2 = new User("billythegate", "Bill", "Gates", "henri.gevenois@proton.com", "windows"
                , new Address("Main street", "1502", null, "56314", "Seattle")
                , new PhoneNumber("+1", "0515236487"), Role.CUSTOMER);
    }

    @Test
    void toDTO_givenAUser_thenReturnADTO()  {
        UserDTO userDTO = userMapper.toDTO(user);

        assertEquals(userDTO.getUserId(), user.getUserId());
        assertEquals(userDTO.getFirstName(),user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getPhoneNumber(),user.getPhoneNumber());
        assertEquals(userDTO.getRole(),user.getRole());
    }

    @Test
    void toListDTO_givenACollectionOfUser_thenReturnAListOfUserDTO(){
        List<User> userList = Lists.newArrayList(user, user2);
        List<UserDTO> userDTOList = userMapper.toListDTO(userList);

        assertEquals(2, userDTOList.size());
        assertEquals(userDTOList.get(0).getUserId(), userList.get(0).getUserId());
        assertEquals(userDTOList.get(1).getUserId(), userList.get(1).getUserId());
    }

    @Test
    void toEntity_givenCreateCustomerDTO_shouldReturnUser() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(user2.getUserId(),
                user2.getFirstName(),
                user2.getLastName(),
                user2.getEmail(),
                user2.getPassword(),
                user2.getAddress(),
                user2.getPhoneNumber());

        User user3 = userMapper.toEntity(createCustomerDTO);

        assertEquals(user3.getUserId(),createCustomerDTO.getUserId());
        assertEquals(user3.getFirstName(),createCustomerDTO.getFirstName());
        assertEquals(user3.getLastName(),createCustomerDTO.getLastName());
        assertEquals(user3.getEmail(),createCustomerDTO.getEmail());
        assertEquals(user3.getAddress(),createCustomerDTO.getAddress());
        assertEquals(user3.getPhoneNumber(),createCustomerDTO.getPhoneNumber());
        assertEquals(user3.getRole(), Role.CUSTOMER);
    }

    @Test
    void toEntity_givenCreateAdministratorDTO_shouldReturnUser() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        CreateAdministratorDTO createAdministratorDTO = new CreateAdministratorDTO(user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getPhoneNumber());

        User user3 = userMapper.toEntity(createAdministratorDTO);

        assertEquals(user3.getUserId(),createAdministratorDTO.getUserId());
        assertEquals(user3.getFirstName(),createAdministratorDTO.getFirstName());
        assertEquals(user3.getLastName(),createAdministratorDTO.getLastName());
        assertEquals(user3.getEmail(),createAdministratorDTO.getEmail());
        assertEquals(user3.getAddress(),createAdministratorDTO.getAddress());
        assertEquals(user3.getPhoneNumber(),createAdministratorDTO.getPhoneNumber());
        assertEquals(user3.getRole(), Role.ADMINISTRATOR);
    }
}

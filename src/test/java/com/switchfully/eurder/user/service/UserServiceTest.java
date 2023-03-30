package com.switchfully.eurder.user.service;

import com.google.common.collect.Lists;
import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.user.service.dtos.CreateAdministratorDTO;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDTO;
import com.switchfully.eurder.user.service.dtos.UserDTO;
import com.switchfully.eurder.user.service.exceptions.InsufficientAccessRightException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserMapper userMapper;

    private final String userId = "henriTheJavaDev";
    private final String firstName = "Henri";
    private final String lastName = "Gevenois";
    private final String email = "henri.gevenois@proton.com";
    private final String password = "henrigvs";
    private Address address;
    private PhoneNumber phoneNumber;

    @BeforeEach
    void setup() {
        address = new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles");
        phoneNumber = new PhoneNumber("+32", "476845621");
    }

    @Test
    void getAllUsers_returnAListOfUserDTO() throws InsufficientAccessRightException, NonExistingUserIdException {
        User newCustomer = new User(userId, firstName, lastName, email, password, address, phoneNumber, Role.CUSTOMER);
        User newAdministrator = new User("AdminId","Admin", "Admin", "admin@admin.admin", "password", address, phoneNumber, Role.ADMINISTRATOR);

        when(userRepository.getUserByUserId("AdminId")).thenReturn(newAdministrator);
        when(userRepository.getAllUsers()).thenReturn(Lists.newArrayList(newCustomer, newAdministrator));

        List<UserDTO> result = userService.getAllUsers("AdminId");
        List<UserDTO> userDTOList = userMapper.toListDTO(Lists.newArrayList(newCustomer, newAdministrator));

        assertEquals(userDTOList, result);
        assertEquals(userDTOList.size(), result.size());
    }

    @Test
    void registerCustomer_givenValidCreateCustomerDTO_thenReturnUserDTO() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, UserIdAlreadyExistsException, EmailAlreadyExistsException, InvalidUserIdFormatException {
        User newCustomer = new User(userId, firstName, lastName, email, password, address, phoneNumber, Role.CUSTOMER);

        when(userRepository.saveUser(any(User.class))).thenReturn(newCustomer);

        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO(userId, firstName, lastName, email, password, address, phoneNumber);
        UserDTO expectedUserDTO = userMapper.toDTO(newCustomer);
        UserDTO userDTO = userService.registerCustomer(createCustomerDTO);

        assertEquals(expectedUserDTO, userDTO);
    }

    @Test
    void registerAdministrator_givenValidCreateAdministratorDTOAndAnAdminID_thenReturnUserDTO() throws UserIdAlreadyExistsException, EmailAlreadyExistsException, InvalidLastNameFormatException, InvalidFirstNameFormatException, InsufficientAccessRightException, InvalidPasswordFormatException, NonExistingUserIdException, InvalidEmailFormatException, InvalidUserIdFormatException {
        User userAdminSavingNewAdmin = new User("AdminId","Admin", "Admin", "admin@admin.admin", "password", address, phoneNumber, Role.ADMINISTRATOR);
        User newAdministrator = new User(userId, firstName, lastName, email, password, address, phoneNumber, Role.ADMINISTRATOR);

        when(userRepository.getUserByUserId("AdminId")).thenReturn(userAdminSavingNewAdmin);
        when(userRepository.saveUser(any(User.class))).thenReturn(newAdministrator);

        CreateAdministratorDTO createAdministratorDTO = new CreateAdministratorDTO(userId, firstName, lastName, email, password, address, phoneNumber);
        UserDTO expectedUserDTO = userMapper.toDTO(newAdministrator);
        UserDTO userDTO = userService.registerAdministrator(createAdministratorDTO, "AdminId");

        assertEquals(expectedUserDTO, userDTO);
    }

    @Test
    void searchUserByUserId_givenExistingUserId_thenReturnUserDTO() throws NonExistingUserIdException, InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        User adminSearchingUser = new User("AdminId","Admin", "Admin", "admin@admin.admin", "password", address, phoneNumber, Role.ADMINISTRATOR);
        User user = new User(userId, firstName, lastName, email, password, address, phoneNumber, Role.CUSTOMER);

        when(userRepository.getUserByUserId("AdminId")).thenReturn(adminSearchingUser);
        when(userRepository.getUserByUserId(userId)).thenReturn(user);

        UserDTO userDTO = userService.searchUserByUserId(userId, "AdminId");

        assertEquals(userMapper.toDTO(user), userDTO);
    }

    @Test
    void searchUserByEmailAndByPassword_givenExistingEmailAndPassword_thenReturnUserDTO() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException, UserIdAlreadyExistsException, EmailAlreadyExistsException, NoEmailPasswordMatchException, NonExistingEmailException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(userId, firstName, lastName, email, hashedPassword, address, phoneNumber, Role.CUSTOMER);

        when(userRepository.getUserByEmail(email)).thenReturn(user);

        UserDTO userDTO = userService.searchUserByEmailAndByPassword(password, email);

        assertNotNull(userDTO);
        assertEquals(userMapper.toDTO(user), userDTO);
    }
}
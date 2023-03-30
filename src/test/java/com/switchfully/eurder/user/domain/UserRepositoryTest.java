package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.address.exception.InvalidAddressFormatException;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.user.domain.phonenumber.exception.InvalidPhoneNumberFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;
    User newUser;

    @BeforeEach
    void setUp() throws InvalidAddressFormatException, InvalidPhoneNumberFormatException, InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException, UserIdAlreadyExistsException, EmailAlreadyExistsException {
        userRepository = new UserRepository();
        newUser = new User("henriTheJavaDev", "Henri", "Gevenois", "henri.gevenois@proton.com", "1234"
                , new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles")
                , new PhoneNumber("+32", "476845621")
                , Role.ADMINISTRATOR);
        userRepository.saveUser(newUser);
    }

    @Test
    void saveUser_givenUniqueEmailAndUserId_thenTheRepositoryShouldContainsTheUser(){
        assertThat(userRepository.getAllUsers().contains(newUser));
    }

    @Test
    void saveUser_givenNotUniqueEmail_shouldThrowEmailAlreadyExistsException() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException, InvalidAddressFormatException, InvalidPhoneNumberFormatException {
        User user2 = new User("billythegate", "Bill", "Gates", "henri.gevenois@proton.com", "windows"
                , new Address("Main street", "1502", null, "56314", "Seattle")
                , new PhoneNumber("+1", "0515236487"), Role.CUSTOMER);

        assertThrows(EmailAlreadyExistsException.class, () -> userRepository.saveUser(user2));
    }

    @Test
    void saveUser_givenNotUniqueUserId_thenShouldThrowUserIdAlreadyExistsException() throws EmailAlreadyExistsException, UserIdAlreadyExistsException, InvalidAddressFormatException, InvalidPhoneNumberFormatException, InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        User user2 = new User("henriTheJavaDev", "Bill", "Gates", "bill.gates@microsoft.com", "windows"
                , new Address("Main street", "1502", null, "56314", "Seattle")
                , new PhoneNumber("+1", "0515236487"), Role.CUSTOMER);

        assertThrows(UserIdAlreadyExistsException.class, () -> userRepository.saveUser(user2));
    }

    @Test
    void getUserByUserId_givenExistingUserId_shouldReturnUser() throws NonExistingUserIdException {
        assertNotNull(userRepository.getUserByUserId("henriTheJavaDev"));
    }

    @Test
    void getUserByUserId_givenNonExistingUserId_shouldReturnNull() {
        assertNull(userRepository.getUserByUserId("theBabyBoss"));
    }

    @Test
    void getUserByEmail_givenExistingEmail_shouldReturnUser() throws NonExistingEmailException {
        assertNotNull(userRepository.getUserByEmail("henri.gevenois@proton.com"));
    }

    @Test
    void getUserByEmail_givenNonExistingEmail_shouldReturnNull() throws NonExistingEmailException {
        assertNull(userRepository.getUserByEmail("bill.gates@microsoft.com"));
    }
}

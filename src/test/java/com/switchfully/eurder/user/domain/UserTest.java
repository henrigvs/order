package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.address.exception.InvalidAddressFormatException;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import com.switchfully.eurder.user.domain.phonenumber.exception.InvalidPhoneNumberFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Address address;
    private PhoneNumber phoneNumber;
    private Role role;

    @BeforeEach
    void setup() throws InvalidAddressFormatException, InvalidPhoneNumberFormatException {
        address =  new Address("Rue Neuve", "58", "8A", "1000", "Bruxelles");
        phoneNumber = new PhoneNumber("+32", "476845621");
        userId = "henriTheJavaDev";
        firstName = "Henri";
        lastName = "Gevenois";
        email = "henri.gevenois@proton.com";
        password = "1234";
        role = Role.ADMINISTRATOR;
        
    }

    @Test
    void validUser() {
        assertDoesNotThrow(() -> new User(userId, firstName, lastName, email, password, address, phoneNumber, role));
    }

    @Test
    void invalidUser_userId() {
        assertThrows(InvalidUserIdFormatException.class, () -> new User("", firstName, lastName, email, password, address, phoneNumber, role));
    }

    @Test
    void invalidUser_firstName() {
        assertThrows(InvalidFirstNameFormatException.class, () -> new User(userId, "h3nri", lastName, email, password, address, phoneNumber, role));
    }

    @Test
    void invalidUser_lastName() {
        assertThrows(InvalidLastNameFormatException.class, () -> new User(userId, firstName, "G3v3nois", email, password, address, phoneNumber, role));
    }

    @Test
    void invalidUser_email() {
        assertThrows(InvalidEmailFormatException.class, () -> new User(userId, firstName, lastName, "henri.gevenoisATproton.com", password, address, phoneNumber, role));
    }

    @Test
    void invalidUser_password(){
        assertThrows(InvalidPasswordFormatException.class, () -> new User(userId, firstName, lastName, email, "", address, phoneNumber, role));
    }


    @Test
    void userEquality() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidUserIdFormatException, InvalidEmailFormatException, InvalidPasswordFormatException {
        User user1 = new User(userId, firstName, lastName, email, password, address, phoneNumber, role);
        User user2 = new User(userId, firstName, lastName, email, password, address, phoneNumber, role);
        assertEquals(user1, user2);
    }

    @Test
    void userInequality() throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidUserIdFormatException, InvalidEmailFormatException, InvalidPasswordFormatException {
        User user1 = new User(userId, firstName, lastName, email, password, address, phoneNumber, role);
        User user2 = new User("henrigvs", firstName, lastName, email, password, address, phoneNumber, role);
        assertNotEquals(user1, user2);
    }
}

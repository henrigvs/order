package com.switchfully.eurder.user.domain.phonenumber;

import com.switchfully.eurder.user.domain.phonenumber.exception.InvalidPhoneNumberFormatException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void validPhoneNumber() {
        assertDoesNotThrow(() -> new PhoneNumber("+32", "476945638"));
    }

    @Test
    void invalidPhoneNumber_prefix() {
        assertThrows(InvalidPhoneNumberFormatException.class, () -> new PhoneNumber("+32222", "476945638"));
    }

    @Test
    void invalidPhoneNumber_localNumber() {
        assertThrows(InvalidPhoneNumberFormatException.class, () -> new PhoneNumber("+32", "4765245334151"));
    }

    @Test
    void phoneNumberEquality() throws InvalidPhoneNumberFormatException {
        PhoneNumber phoneNumber1 = new PhoneNumber("+32", "476945638");
        PhoneNumber phoneNumber2 = new PhoneNumber("+32", "476945638");
        assertEquals(phoneNumber1, phoneNumber2);
    }

    @Test
    void phoneNumberInequality() throws InvalidPhoneNumberFormatException {
        PhoneNumber phoneNumber1 = new PhoneNumber("+1", "476945638");
        PhoneNumber phoneNumber2 = new PhoneNumber("+1", "476945635");
        assertNotEquals(phoneNumber1, phoneNumber2);
    }
}

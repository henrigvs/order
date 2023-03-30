package com.switchfully.eurder.user.domain.address;

import com.switchfully.eurder.user.domain.address.exception.InvalidAddressFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private String street;
    private String number;
    private String box;
    private String zip;
    private String city;

    @BeforeEach
    void setup(){
        street = "Rue Neuve";
        number= "58";
        box = "8A";
        zip = "1000";
        city = "Bruxelles";
    }
    @Test
    void validAddress() {
        assertDoesNotThrow(() -> new Address(street, number, box, zip, city));
    }

    @Test
    void invalidAddress_street() {
        assertThrows(InvalidAddressFormatException.class, () -> new Address("", number, box, zip, city));
    }

    @Test
    void invalidAddress_number() {
        assertThrows(InvalidAddressFormatException.class, () -> new Address(street, "12A", box, zip, city));
    }

    @Test
    void invalidAddress_zip() {
        assertThrows(InvalidAddressFormatException.class, () -> new Address(street, number, box, "1#00", city));
    }

    @Test
    void invalidAddress_city() {
        assertThrows(InvalidAddressFormatException.class, () -> new Address(street, number, box, zip, "Brux3ll3s"));
    }

    @Test
    void addressEquality() throws InvalidAddressFormatException {
        Address address1 = new Address(street, number, box, zip, city);
        Address address2 = new Address(street, number, box, zip, city);
        assertEquals(address1, address2);
    }

    @Test
    void addressInequality() throws InvalidAddressFormatException {
        Address address1 = new Address(street, number, box, zip, city);
        Address address2 = new Address(street, "124", box, zip, city);
        assertNotEquals(address1, address2);
    }
}

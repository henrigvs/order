package com.switchfully.eurder.user.domain.address;

import com.switchfully.eurder.user.domain.address.exception.InvalidAddressFormatException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Address {

    private String street;
    private String number;
    private String box;
    private String zip;
    private String city;

    private final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]{1,5}$");
    private final Pattern CITY_PATTERN = Pattern.compile("^[a-zA-Z\\- ]+$");
    private final Pattern ZIP_PATTERN = Pattern.compile("^[A-Z0-9]{1,8}$");

    public Address(String street, String number, String box, String zip, String city) throws InvalidAddressFormatException {
        if(!NUMBER_PATTERN.matcher(number).matches())
            throw new InvalidAddressFormatException("Wrong number format");
        if(!CITY_PATTERN.matcher(city).matches())
            throw new InvalidAddressFormatException("Wrong city format");
        if(!ZIP_PATTERN.matcher(zip).matches())
            throw new InvalidAddressFormatException("Wrong zip format");
        if(street == null || street == "")
            throw new InvalidAddressFormatException("Wrong street format");
        this.street = street;
        this.number = number;
        this.box = box;
        this.zip = zip;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getBox() {
        return box;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(street, address.street) && Objects.equals(number, address.number) && Objects.equals(box, address.box) && Objects.equals(zip, address.zip) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, box, zip, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", box='" + box + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

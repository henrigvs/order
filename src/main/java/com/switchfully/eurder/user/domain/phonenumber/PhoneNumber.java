package com.switchfully.eurder.user.domain.phonenumber;

import com.switchfully.eurder.user.domain.phonenumber.exception.InvalidPhoneNumberFormatException;
import java.util.Objects;
import java.util.regex.Pattern;

public class PhoneNumber {

    private final String countryCode;
    private final String localNumber;

    private final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^\\+[0-9]{1,3}$");
    private final Pattern LOCAL_NUMBER_PATTERN = Pattern.compile("^[0-9]{1,10}$");

    public PhoneNumber(String countryCode, String localNumber) throws InvalidPhoneNumberFormatException {
        if(!COUNTRY_CODE_PATTERN.matcher(countryCode).matches())
            throw new InvalidPhoneNumberFormatException("Invalid phone number prefix");
        if(!LOCAL_NUMBER_PATTERN.matcher(localNumber).matches())
            throw new InvalidPhoneNumberFormatException("Invalid phone number suffix");
        this.localNumber = localNumber;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber that)) return false;
        return Objects.equals(countryCode, that.countryCode) && Objects.equals(localNumber, that.localNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, localNumber);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "countryCode='" + countryCode + '\'' +
                ", localNumber='" + localNumber + '\'' +
                '}';
    }
}

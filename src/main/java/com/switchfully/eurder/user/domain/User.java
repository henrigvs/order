package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;

import java.util.Objects;
import java.util.regex.Pattern;

public class User {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Address address;
    private final PhoneNumber phoneNumber;
    private final Role role;

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$");
    private final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    private final Pattern USER_ID_PATTERN = Pattern.compile("^[0-9a-zA-Z_]+$");

    public User(String userId, String firstName, String lastName, String email, String password, Address address, PhoneNumber phoneNumber, Role role) throws InvalidUserIdFormatException, InvalidFirstNameFormatException, InvalidLastNameFormatException, InvalidEmailFormatException, InvalidPasswordFormatException {
        if (!USER_ID_PATTERN.matcher(userId).matches())
            throw new InvalidUserIdFormatException();
        if (!NAME_PATTERN.matcher(firstName).matches())
            throw new InvalidFirstNameFormatException();
        if (!NAME_PATTERN.matcher(lastName).matches())
            throw new InvalidLastNameFormatException();
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new InvalidEmailFormatException();
        if(password == null || password.equals(""))
            throw new InvalidPasswordFormatException();

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(address, user.address) && Objects.equals(phoneNumber, user.phoneNumber) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, password, address, phoneNumber, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role +
                '}';
    }
}

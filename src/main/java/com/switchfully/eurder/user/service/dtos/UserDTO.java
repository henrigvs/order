package com.switchfully.eurder.user.service.dtos;

import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;

import java.util.Objects;

public class UserDTO {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Address address;
    private final PhoneNumber phoneNumber;
    private final Role role;

    public UserDTO(String userId, String firstName, String lastName, String email, Address address, PhoneNumber phoneNumber, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(userId, userDTO.userId) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(email, userDTO.email) && Objects.equals(address, userDTO.address) && Objects.equals(phoneNumber, userDTO.phoneNumber) && role == userDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, address, phoneNumber, role);
    }
}

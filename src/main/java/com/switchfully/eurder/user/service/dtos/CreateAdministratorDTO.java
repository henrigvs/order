package com.switchfully.eurder.user.service.dtos;

import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;

public class CreateAdministratorDTO {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Address address;
    private final PhoneNumber phoneNumber;
    private final Role role;

    public CreateAdministratorDTO(String userId, String firstName, String lastName, String email, String password, Address address, PhoneNumber phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = Role.ADMINISTRATOR;
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
}

package com.switchfully.eurder.user.service.dtos;

import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;

public class CreateCustomerDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Address address;
    private PhoneNumber phoneNumber;
    private Role role;

    public CreateCustomerDTO(String userId, String firstName, String lastName, String email, String password, Address address, PhoneNumber phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = Role.CUSTOMER;
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

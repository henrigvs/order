package com.switchfully.eurder.user.service.dtos;

import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;

import java.util.Objects;

public record UserDTO(String userId, String firstName, String lastName, String email, Address address,
                      PhoneNumber phoneNumber, Role role) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(userId, userDTO.userId) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(email, userDTO.email) && Objects.equals(address, userDTO.address) && Objects.equals(phoneNumber, userDTO.phoneNumber) && role == userDTO.role;
    }

}

package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.service.dtos.CreateAdministratorDTO;
import com.switchfully.eurder.user.service.dtos.UserDTO;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMapper {

    public UserDTO toDTO(User user){
        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getRole());
    }

    public List<UserDTO> toListDTO(Collection<User> users){
        List<UserDTO> usersToReturn = new ArrayList<>();
        for (User user: users) {
            usersToReturn.add(this.toDTO(user));
        }
        return usersToReturn;
    }


    /** create a member */
    public User toEntity(CreateCustomerDTO createCustomerDTODTO) throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        return new User(createCustomerDTODTO.getUserId(),
                createCustomerDTODTO.getFirstName(),
                createCustomerDTODTO.getLastName(),
                createCustomerDTODTO.getEmail(),
                hashPassword(createCustomerDTODTO.getPassword()),
                createCustomerDTODTO.getAddress(),
                createCustomerDTODTO.getPhoneNumber(),
                createCustomerDTODTO.getRole());
    }

    /** Create an admin */
    public User toEntity(CreateAdministratorDTO createAdministratorDTO) throws InvalidLastNameFormatException, InvalidFirstNameFormatException, InvalidPasswordFormatException, InvalidEmailFormatException, InvalidUserIdFormatException {
        return new User(createAdministratorDTO.getUserId(),
                createAdministratorDTO.getFirstName(),
                createAdministratorDTO.getLastName(),
                createAdministratorDTO.getEmail(),
                hashPassword(createAdministratorDTO.getPassword()),
                createAdministratorDTO.getAddress(),
                createAdministratorDTO.getPhoneNumber(),
                createAdministratorDTO.getRole());
    }

    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

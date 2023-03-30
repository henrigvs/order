package com.switchfully.eurder.user.service;

import com.switchfully.eurder.user.domain.Role;
import com.switchfully.eurder.user.domain.User;
import com.switchfully.eurder.user.domain.UserRepository;
import com.switchfully.eurder.user.domain.exceptions.*;
import com.switchfully.eurder.user.service.dtos.CreateAdministratorDTO;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDTO;
import com.switchfully.eurder.user.service.dtos.UserDTO;
import com.switchfully.eurder.user.service.exceptions.InsufficientAccessRightException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userMapper = new UserMapper();
        this.userRepository = userRepository;
    }

    public UserDTO registerCustomer(CreateCustomerDTO createCustomerDTO) {
        User user = userRepository.saveUser(userMapper.toEntity(createCustomerDTO));
        return userMapper.toDTO(user);
    }

    public UserDTO registerAdministrator(CreateAdministratorDTO createAdministratorDTO, String userId) {
        User adminUser = userRepository.getUserByUserId(userId);
        validateAdminAccess(adminUser);

        User user = userRepository.saveUser(userMapper.toEntity(createAdministratorDTO));
        return userMapper.toDTO(user);
    }

    public UserDTO searchUserByUserId(String userId) {
        User user = this.userRepository.getUserByUserId(userId);
        if (user == null) {
            throw new NonExistingUserIdException();
        }
        return userMapper.toDTO(user);
    }


    public UserDTO searchUserByEmailAndByPassword(String password, String email) {
        User user = userRepository.getUserByEmail(email);
        validateEmail(user);

        if (BCrypt.checkpw(password, user.getPassword())) {
            return userMapper.toDTO(user);
        }
        throw new NoEmailPasswordMatchException();
    }

    public List<UserDTO> getAllUsers(String adminId) {
        User adminUser = userRepository.getUserByUserId(adminId);
        validateAdminAccess(adminUser);

        return userMapper.toListDTO(userRepository.getAllUsers());
    }

    private void validateAdminAccess(User adminUser) {
        if (adminUser == null || !adminUser.getRole().equals(Role.ADMINISTRATOR)) {
            throw new InsufficientAccessRightException();
        }
    }

    private void validateEmail(User user) {
        if (user == null) {
            throw new NonExistingEmailException();
        }
    }
}

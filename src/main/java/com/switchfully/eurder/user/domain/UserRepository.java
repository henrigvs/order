package com.switchfully.eurder.user.domain;

import com.switchfully.eurder.user.domain.address.Address;
import com.switchfully.eurder.user.domain.exceptions.EmailAlreadyExistsException;
import com.switchfully.eurder.user.domain.exceptions.NonExistingUserIdException;
import com.switchfully.eurder.user.domain.exceptions.UserIdAlreadyExistsException;
import com.switchfully.eurder.user.domain.phonenumber.PhoneNumber;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final ConcurrentHashMap<String, User> userRepository;

    public UserRepository() {
        userRepository = new ConcurrentHashMap<>();
        createInitialAdmin();
    }

    private void createInitialAdmin() {
        User initialAdmin = new User("initialAdmin", "Admin", "Admin", "admin@admin.com", "adminPassword",
                new Address("no address", "0", null, "0000", "Bruxelles"),
                new PhoneNumber("+32", "000000000"),
                Role.ADMINISTRATOR);
        userRepository.put(initialAdmin.getUserId(), initialAdmin);
    }

    public User saveUser(User user) {
        if (!isEmailUnique(user.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        if (!isUserIdUnique(user.getUserId())) {
            throw new UserIdAlreadyExistsException();
        }
        userRepository.put(user.getUserId(), user);
        return user;
    }

    public User deleteUser(String userId)  {
        if (!userRepository.containsKey(userId)) {
            throw new NonExistingUserIdException();
        }
        return userRepository.remove(userId);
    }

    public User getUserByUserId(String userId) {
        return userRepository.get(userId);
    }

    public User getUserByEmail(String email) {
        return userRepository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Collection<User> getAllUsers() {
        return userRepository.values();
    }

    private boolean isEmailUnique(String email) {
        return userRepository.values().stream()
                .noneMatch(user -> user.getEmail().equals(email));
    }

    private boolean isUserIdUnique(String userId) {
        return !userRepository.containsKey(userId);
    }
}

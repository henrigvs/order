package com.switchfully.eurder.user.api;

import com.switchfully.eurder.user.service.UserService;
import com.switchfully.eurder.user.service.dtos.CreateAdministratorDTO;
import com.switchfully.eurder.user.service.dtos.CreateCustomerDTO;
import com.switchfully.eurder.user.service.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** GET */

    @GetMapping(path = "/allUsers", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers(@RequestHeader String adminId){
        return userService.getAllUsers(adminId);
    }

    @GetMapping(path = "/user/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO searchUserByUserId(@PathVariable String userId, @RequestHeader String adminId){
        return userService.searchUserByUserId(userId, adminId);
    }



    /** POST */


    @PostMapping(path = "/customer", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerCustomer(@RequestBody CreateCustomerDTO createCustomerDTO){
        return userService.registerCustomer(createCustomerDTO);
    }

    @PostMapping(path = "/admin", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerAdministrator(@RequestBody CreateAdministratorDTO createAdministratorDTO, @RequestHeader String adminId){
        return userService.registerAdministrator(createAdministratorDTO, adminId);
    }
}

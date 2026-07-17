package com.helpdesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.model.LoginRequest;
import com.helpdesk.model.User;
import com.helpdesk.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    // CREATE USER
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        System.out.println("Login API Called");

        return service.login(request.getEmail(), request.getPassword());
    }

    // LOGOUT
    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7); // Remove "Bearer "

        return service.logout(token);
    }

    // GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return service.getUserById(id);
    }

    // GET USER BY EMPLOYEE ID
    @GetMapping("/employee/{employeeId}")
    public User getByEmployeeId(@PathVariable String employeeId) {
        return service.getByEmployeeId(employeeId);
    }

    // UPDATE USER BY EMPLOYEE ID
    @PutMapping("/employee/{employeeId}")
    public User updateByEmployeeId(@PathVariable String employeeId,
                                   @RequestBody User user) {
        return service.updateByEmployeeId(employeeId, user);
    }

    // DELETE USER BY EMPLOYEE ID
    @DeleteMapping("/employee/{employeeId}")
    public String deleteByEmployeeId(@PathVariable String employeeId) {
        return service.deleteByEmployeeId(employeeId);
    }

    // DELETE USER BY ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return "User Deleted Successfully";
    }

    // CHANGE PASSWORD
    @PutMapping("/change-password")
    public String changePassword(@RequestBody LoginRequest request) {

        return service.changePassword(
                request.getEmail(),
                request.getPassword(),
                request.getNewPassword()
        );
    }
}
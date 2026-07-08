package com.helpdesk.controller;

import com.helpdesk.model.LoginRequest;
import com.helpdesk.model.User;
import com.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return service.getUserById(id);
    }

    // GET BY EMPLOYEE ID
    @GetMapping("/employee/{employeeId}")
    public User getByEmployeeId(@PathVariable String employeeId) {
        return service.getByEmployeeId(employeeId);
    }

    // UPDATE BY EMPLOYEE ID
    @PutMapping("/employee/{employeeId}")
    public User updateByEmployeeId(@PathVariable String employeeId,
                                   @RequestBody User user) {
        return service.updateByEmployeeId(employeeId, user);
    }

    // DELETE BY EMPLOYEE ID
    @DeleteMapping("/employee/{employeeId}")
    public String deleteByEmployeeId(@PathVariable String employeeId) {
        return service.deleteByEmployeeId(employeeId);
    }

    // DELETE BY ID
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
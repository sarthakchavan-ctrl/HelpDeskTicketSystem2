package com.helpdesk.service;

import com.helpdesk.model.User;
import com.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.helpdesk.config.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    // CREATE USER
    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus("ACTIVE");
        user.setCreatedDate(LocalDate.now());

        return userRepository.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET USER BY ID
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // GET USER BY EMPLOYEE ID
    public User getByEmployeeId(String employeeId) {
        return userRepository.findByEmployeeId(employeeId);
    }

    // UPDATE USER
    public User updateByEmployeeId(String employeeId, User user) {

        User existing = userRepository.findByEmployeeId(employeeId);

        if (existing == null) {
            return null;
        }

        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        existing.setPhone(user.getPhone());
        existing.setDepartment(user.getDepartment());
        existing.setRole(user.getRole());
        existing.setStatus(user.getStatus());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existing);
    }

    // DELETE USER BY EMPLOYEE ID
    public String deleteByEmployeeId(String employeeId) {

        User user = userRepository.findByEmployeeId(employeeId);

        if (user == null) {
            return "User not found";
        }

        userRepository.delete(user);

        return "User deleted successfully";
    }

    // DELETE USER BY ID
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // LOGIN
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User not found";
        }

        if (passwordEncoder.matches(password, user.getPassword())) {

            String token = jwtUtil.generateToken(user.getEmail());

            return token;
        }

        return "Invalid password";
    }

    // CHANGE PASSWORD
    public String changePassword(String email,
                                 String oldPassword,
                                 String newPassword) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "User not found";
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect";
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return "Password changed successfully";
    }
}
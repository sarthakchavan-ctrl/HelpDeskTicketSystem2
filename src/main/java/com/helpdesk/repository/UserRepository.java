package com.helpdesk.repository;

import com.helpdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find User By Email
    User findByEmail(String email);

    // Find User By Employee ID
    User findByEmployeeId(String employeeId);

    // Login / Change Password
    User findByEmailAndPassword(String email, String password);
}

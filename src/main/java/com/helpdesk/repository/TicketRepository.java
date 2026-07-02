package com.helpdesk.repository;

import com.helpdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Search By Status
    List<Ticket> findByStatus(String status);

    // Search By Priority
    List<Ticket> findByPriority(String priority);

    // Search By Category
    List<Ticket> findByCategory(String category);

    // Search By Assigned To
    List<Ticket> findByAssignedTo(String assignedTo);

}
package com.helpdesk.service;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;

@Service
public class TicketService {




    

    @Autowired
    private TicketRepository repository;

    

    // Create Ticket
    public Ticket createTicket(Ticket ticket) {

        setDueDate(ticket);

        return repository.save(ticket);
    }

    // Get All Tickets
    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    // Get Ticket By ID
    public Ticket getTicketById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // Update Ticket
    public Ticket updateTicket(Ticket ticket) {

        setDueDate(ticket);

        return repository.save(ticket);
    }

    // Delete Ticket
    public void deleteTicket(Integer id) {
        repository.deleteById(id);
    }

    // Reopen Closed Ticket
    public Ticket reopenTicket(Integer ticketId) {

        Ticket ticket = repository.findById(ticketId).orElse(null);

        if (ticket == null) {
            throw new RuntimeException("Ticket Not Found");
        }

        if (!ticket.getStatus().equalsIgnoreCase("CLOSED")) {
            throw new RuntimeException("Only Closed Tickets Can Be Reopened");
        }

        ticket.setStatus("REOPENED");

        return repository.save(ticket);
    }

    // Escalate Ticket
    public Ticket escalateTicket(Integer ticketId) {

        Ticket ticket = repository.findById(ticketId).orElse(null);

        if (ticket == null) {
            throw new RuntimeException("Ticket Not Found");
        }

        if (!(ticket.getStatus().equalsIgnoreCase("OPEN")
                || ticket.getStatus().equalsIgnoreCase("IN_PROGRESS"))) {

            throw new RuntimeException("Only OPEN or IN_PROGRESS tickets can be escalated.");
        }

        if (ticket.getDueDate() != null &&
                LocalDate.now().isAfter(ticket.getDueDate())) {

            ticket.setStatus("ESCALATED");

            return repository.save(ticket);

        } else {

            throw new RuntimeException("Ticket is not overdue. Cannot escalate.");
        }
    }

    // Bulk Update Tickets
    @Transactional
    public List<Ticket> bulkUpdateTickets(List<Ticket> tickets) {

        for (Ticket ticket : tickets) {
            setDueDate(ticket);
        }

        return repository.saveAll(tickets);
    }

    // Search By Status
    public List<Ticket> getTicketsByStatus(String status) {
        return repository.findByStatus(status);
    }

    // Search By Priority
    public List<Ticket> getTicketsByPriority(String priority) {
        return repository.findByPriority(priority);
    }

    // Search By Category
    public List<Ticket> getTicketsByCategory(String category) {
        return repository.findByCategory(category);
    }

    // Search By Assigned To
    public List<Ticket> getTicketsByAssignedTo(String assignedTo) {
        return repository.findByAssignedTo(assignedTo);
    }

    // Merge Duplicate Tickets
    public String mergeDuplicateTickets(Integer originalTicketId, Integer duplicateTicketId) {

        Ticket originalTicket = repository.findById(originalTicketId).orElse(null);
        Ticket duplicateTicket = repository.findById(duplicateTicketId).orElse(null);

        if (originalTicket == null) {
            throw new RuntimeException("Original Ticket Not Found");
        }

        if (duplicateTicket == null) {
            throw new RuntimeException("Duplicate Ticket Not Found");
        }

        repository.deleteById(duplicateTicketId);

        return "Duplicate Ticket " + duplicateTicketId +
                " merged into Ticket " + originalTicketId;
    }

    // Common Method
    // Common Method
    private void setDueDate(Ticket ticket) {

    if (ticket.getPriority() == null) {
        ticket.setDueDate(LocalDate.now().plusDays(7));
        return;
    }

    switch (ticket.getPriority().toUpperCase()) {

        case "LOW":
            ticket.setDueDate(LocalDate.now().plusDays(7));
            break;

        case "MEDIUM":
            ticket.setDueDate(LocalDate.now().plusDays(5));
            break;

        case "HIGH":
            ticket.setDueDate(LocalDate.now().plusDays(2));
            break;

        case "CRITICAL":
            ticket.setDueDate(LocalDate.now().plusDays(1));
            break;

        default:
            ticket.setDueDate(LocalDate.now().plusDays(7));
            break;
    }
}
    // Clone Ticket
    public Ticket cloneTicket(Integer ticketId, Integer newTicketId) {

        Ticket existingTicket = repository.findById(ticketId).orElse(null);

        if (existingTicket == null) {
            throw new RuntimeException("Original Ticket Not Found");
        }

        if (repository.findById(newTicketId).isPresent()) {
            throw new RuntimeException("New Ticket ID Already Exists");
        }

        Ticket clonedTicket = new Ticket();

        clonedTicket.setTicketId(newTicketId);
        clonedTicket.setTitle(existingTicket.getTitle());
        clonedTicket.setDescription(existingTicket.getDescription());
        clonedTicket.setStatus(existingTicket.getStatus());
        clonedTicket.setPriority(existingTicket.getPriority());
        clonedTicket.setCategory(existingTicket.getCategory());
        clonedTicket.setSubcategory(existingTicket.getSubcategory());
        clonedTicket.setAssignedTo(existingTicket.getAssignedTo());
        clonedTicket.setDueDate(existingTicket.getDueDate());

        return repository.save(clonedTicket);
    }
}





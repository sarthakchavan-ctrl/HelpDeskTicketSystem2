package com.helpdesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.model.Ticket;
import com.helpdesk.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService service;

    // Create Ticket
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return service.createTicket(ticket);
    }

    // Get All Tickets
    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }

    // Get Ticket By ID
    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return service.getTicketById(id);
    }

    // Update Ticket
    @PutMapping
    public Ticket updateTicket(@RequestBody Ticket ticket) {
        return service.updateTicket(ticket);
    }

    // Delete Ticket
    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Integer id) {
        service.deleteTicket(id);
        return "Ticket Deleted Successfully";
    }

    // Reopen Closed Ticket
    @PutMapping("/reopen/{ticketId}")
    public Ticket reopenTicket(@PathVariable Integer ticketId) {
        return service.reopenTicket(ticketId);
    }

    // Escalate Ticket
    @PutMapping("/escalate/{ticketId}")
    public Ticket escalateTicket(@PathVariable Integer ticketId) {
        return service.escalateTicket(ticketId);
    }

    // Bulk Update Tickets
    @PutMapping("/bulk-update")
    public List<Ticket> bulkUpdateTickets(@RequestBody List<Ticket> tickets) {
        return service.bulkUpdateTickets(tickets);
    }

    // Search By Status
    @GetMapping("/status/{status}")
    public List<Ticket> getTicketsByStatus(@PathVariable String status) {
        return service.getTicketsByStatus(status);
    }

    // Search By Priority
    @GetMapping("/priority/{priority}")
    public List<Ticket> getTicketsByPriority(@PathVariable String priority) {
        return service.getTicketsByPriority(priority);
    }

    // Search By Category
    @GetMapping("/category/{category}")
    public List<Ticket> getTicketsByCategory(@PathVariable String category) {
        return service.getTicketsByCategory(category);
    }

    // Search By Assigned To
    @GetMapping("/assigned/{assignedTo}")
    public List<Ticket> getTicketsByAssignedTo(@PathVariable String assignedTo) {
        return service.getTicketsByAssignedTo(assignedTo);
        
    }

    // Merge Duplicate Tickets
    @DeleteMapping("/merge")
    public String mergeDuplicateTickets(
            @RequestParam Integer originalTicketId,
            @RequestParam Integer duplicateTicketId) {

        return service.mergeDuplicateTickets(originalTicketId, duplicateTicketId);
    }
    // Clone Ticket
    @PostMapping("/clone/{ticketId}")
    public Ticket cloneTicket(
            @PathVariable Integer ticketId,
            @RequestParam Integer newTicketId) {

        return service.cloneTicket(ticketId, newTicketId);
    }
}








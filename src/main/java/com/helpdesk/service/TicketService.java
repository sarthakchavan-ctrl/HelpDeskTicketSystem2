package com.helpdesk.service;

import com.helpdesk.model.Ticket;
import com.helpdesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    // Create Ticket
    public Ticket createTicket(Ticket ticket) {
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
        return repository.save(ticket);
    }

    // Delete Ticket
    public void deleteTicket(Integer id) {
        repository.deleteById(id);
    }
}
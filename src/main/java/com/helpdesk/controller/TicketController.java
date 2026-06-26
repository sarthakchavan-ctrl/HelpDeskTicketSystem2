package com.helpdesk.controller;

import com.helpdesk.model.Ticket;
import com.helpdesk.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService service;

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return service.createTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return service.getTicketById(id);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Integer id,
                               @RequestBody Ticket ticket) {
        ticket.setTicketId(id);
        return service.updateTicket(ticket);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Integer id) {
        service.deleteTicket(id);
        return "Ticket Deleted Successfully";
    }
}
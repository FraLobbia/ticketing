package com.backend.service.impl;

import com.backend.model.Ticket;
import com.backend.repository.TicketRepository;
import com.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

  @Autowired
  private TicketRepository ticketRepository;

  @Override
  public List<Ticket> getAllTickets() {
    return ticketRepository.findAll();
  }

  @Override
  public Optional<Ticket> getTicketById(Long id) {
    return ticketRepository.findById(id);
  }

  @Override
  public Ticket saveTicket(Ticket ticket) {
    ticket.setCreatedAt(LocalDateTime.now());
    ticket.setUpdatedAt(LocalDateTime.now());
    return ticketRepository.save(ticket);
  }

  @Override
  public Ticket updateTicket(Long id, Ticket ticketDetails) {
    return ticketRepository.findById(id).map(ticket -> {
      ticket.setTitle(ticketDetails.getTitle());
      ticket.setDescription(ticketDetails.getDescription());
      ticket.setStatus(ticketDetails.getStatus());
      ticket.setUser(ticketDetails.getUser());
      ticket.setUpdatedAt(LocalDateTime.now());
      return ticketRepository.save(ticket);
    }).orElseThrow(() -> new RuntimeException("Ticket not found"));
  }

  @Override
  public void deleteTicket(Long id) {
    if (ticketRepository.existsById(id)) {
      ticketRepository.deleteById(id);
    } else {
      throw new RuntimeException("Ticket not found");
    }
  }
}

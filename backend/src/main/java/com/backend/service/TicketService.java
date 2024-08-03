package com.backend.service;

import com.backend.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

  List<Ticket> getAllTickets();

  Optional<Ticket> getTicketById(Long id);

  Ticket saveTicket(Ticket ticket);

  Ticket updateTicket(Long id, Ticket ticketDetails);

  void deleteTicket(Long id);
}

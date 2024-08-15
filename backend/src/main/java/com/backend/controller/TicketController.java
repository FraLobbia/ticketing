package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.DTO.TicketRequestDto;
import com.backend.model.DTO.TicketResponseDto;
import com.backend.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @PostMapping
  public TicketResponseDto createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
    return ticketService.createTicket(ticketRequestDto);
  }

  @GetMapping("/{id}")
  public TicketResponseDto getTicketById(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }

  @GetMapping
  public List<TicketResponseDto> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @PutMapping("/{id}")
  public TicketResponseDto updateTicket(@PathVariable Long id, @RequestBody TicketRequestDto ticketRequestDto) {
    return ticketService.updateTicket(id, ticketRequestDto);
  }

  @DeleteMapping("/{id}")
  public void deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
  }
}

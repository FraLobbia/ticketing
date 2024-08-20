package com.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.DTO.request.TicketRequestDto;
import com.backend.model.DTO.response.TicketResponseDto;
import com.backend.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  /**
   * Crea un nuovo ticket basato sul TicketRequestDto fornito.
   *
   * @param ticketRequestDto L'oggetto DTO che contiene i dettagli del ticket.
   * @return TicketResponseDto Il ticket creato.
   */
  @PostMapping
  public TicketResponseDto createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
    return ticketService.createTicket(ticketRequestDto);
  }

  /**
   * Recupera un ticket tramite il suo ID.
   *
   * @param id L'ID del ticket da recuperare.
   * @return TicketResponseDto Il ticket con l'ID specificato.
   */
  @GetMapping("/{id}")
  public TicketResponseDto getTicketById(@PathVariable Long id) {
    return ticketService.getTicketById(id);
  }

  /**
   * Recupera una lista di tutti i ticket.
   *
   * @return List<TicketResponseDto> Una lista di tutti i ticket.
   */
  @GetMapping
  public List<TicketResponseDto> getAllTickets() {
    return ticketService.getAllTickets();
  }

  /**
   * Aggiorna un ticket esistente in base al suo ID e al TicketRequestDto fornito.
   *
   * @param id               L'ID del ticket da aggiornare.
   * @param ticketRequestDto L'oggetto DTO che contiene i dettagli aggiornati del
   *                         ticket.
   * @return TicketResponseDto Il ticket aggiornato.
   */
  @PutMapping("/{id}")
  public TicketResponseDto updateTicket(@PathVariable Long id, @RequestBody TicketRequestDto ticketRequestDto) {
    return ticketService.updateTicket(id, ticketRequestDto);
  }

  /**
   * Cancella un ticket tramite il suo ID.
   *
   * @param id L'ID del ticket da cancellare.
   */
  @DeleteMapping("/{id}")
  public void deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
  }

  /**
   * Recupera una lista di ticket in base a un array di ID.
   *
   * @param ids Una stringa di ID separati da virgole.
   * @return List<TicketResponseDto> Una lista di ticket con gli ID specificati.
   */
  @GetMapping("/viewing-tickets")
  public List<TicketResponseDto> getViewingTickets(String ids) {
    List<Long> ticketIds = Arrays.stream(ids.split(","))
        .map(Long::parseLong)
        .collect(Collectors.toList());

    List<TicketResponseDto> tickets = new ArrayList<>();
    for (Long ticketId : ticketIds) {
      tickets.add(ticketService.getTicketById(ticketId));
    }
    return tickets;
  }
}

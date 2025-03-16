package com.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.models.dtos.request.TicketRequestDto;
import com.app.models.dtos.response.TicketResponseDto;
import com.app.models.enums.TicketStatusEnum;
import com.app.services.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

@Autowired
  private  TicketService ticketService;


  /**
   * Crea un nuovo ticket basato sul TicketRequestDto fornito.
   *
   * @param ticketRequestDto L'oggetto DTO che contiene i dettagli del ticket.
   * @return TicketResponseDto Il ticket creato.
   */
  @PostMapping
  public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
	  ticketRequestDto.setStatus(TicketStatusEnum.OPEN);
    TicketResponseDto ticket = ticketService.save(ticketRequestDto);
    if (ticket == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.status(201).body(ticket);
  }

  /**
   * Recupera un ticket tramite il suo ID.
   *
   * @param id L'ID del ticket da recuperare.
   * @return TicketResponseDto Il ticket con l'ID specificato.
   */
  @GetMapping("/{id}")
  public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
	  	TicketResponseDto ticket = ticketService.findById(id).orElse(null);
    if (ticket == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok(ticket);
  }

  /**
   * Recupera una lista di tutti i ticket.
   *
   * @return List<TicketResponseDto> Una lista di tutti i ticket.
   */
  @GetMapping
  public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
    List<TicketResponseDto> tickets = ticketService.getAllTickets();
    if (tickets == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok(tickets);
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
  public ResponseEntity<TicketResponseDto> updateTicket(@PathVariable Long id,
      @RequestBody TicketRequestDto ticketRequestDto) {
    TicketResponseDto updatedTicket = ticketService.updateTicket(id, ticketRequestDto);
    if (updatedTicket == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok(updatedTicket);
  }

  /**
   * Cancella un ticket tramite il suo ID.
   *
   * @param id L'ID del ticket da cancellare.
   * @return void
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Recupera una lista di ticket in base a un array di ID.
   *
   * @param ids Una stringa di ID separati da virgole.
   * @return List<TicketResponseDto> Una lista di ticket con gli ID specificati.
   */
  @GetMapping("/viewing-tickets")
  public ResponseEntity<List<TicketResponseDto>> getViewingTickets(String ids) {
    List<TicketResponseDto> tickets = ticketService.getTicketsByIds(ids);
    if (tickets == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok(tickets);
  }

  /**
   * Aggiorna lo stato di un ticket.
   *
   * @param id     L'ID del ticket da aggiornare.
   * @param status Il nuovo stato del ticket.
   * @return TicketResponseDto Il ticket aggiornato.
   */
  @PutMapping("/status/{id}")
  public ResponseEntity<TicketResponseDto> updateTicketStatus(@PathVariable Long id,
      @RequestBody Map<String, String> status) {
    String statusString = status.get("status");
    TicketResponseDto updatedTicket = ticketService.updateTicketStatus(id, statusString);
    if (updatedTicket == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok(updatedTicket);
  }
}

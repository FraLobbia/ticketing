package com.backend.service;

import java.util.List;

import com.backend.model.DTO.TicketRequestDto;
import com.backend.model.DTO.TicketResponseDto;

/**
 * TicketService
 * 
 * @see Implementazione => com.backend.service.impl.TicketServiceImpl
 */
public interface TicketService {

  /**
   * Crea un nuovo ticket nel database con i dettagli forniti nel
   * ticketRequestDto.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#createTicket
   * @param ticketRequestDto
   * @return TicketResponseDto con i dettagli del ticket appena creato.
   */
  TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);

  /**
   * Restituisce un ticket dal database in base all'id fornito.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#getTicketById
   * @param id
   * @return TicketResponseDto con i dettagli del ticket richiesto.
   */
  TicketResponseDto getTicketById(Long id);

  /**
   * Restituisce tutti i ticket presenti nel database.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#getAllTickets
   * @return List<TicketResponseDto> con i dettagli di tutti i ticket presenti nel
   *         database.
   */
  List<TicketResponseDto> getAllTickets();

  /**
   * Aggiorna un ticket nel database con i dettagli forniti nel ticketRequestDto.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#updateTicket
   * @param id
   * @param ticketRequestDto
   * @return TicketResponseDto con i dettagli del ticket appena aggiornato.
   */
  TicketResponseDto updateTicket(Long id, TicketRequestDto ticketRequestDto);

  /**
   * Elimina un ticket dal database in base all'id fornito.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#deleteTicket
   * @param id
   * @return void
   */
  void deleteTicket(Long id);
}

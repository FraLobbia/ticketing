package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.model.DTO.request.TicketRequestDto;
import com.backend.model.DTO.response.TicketResponseDto;

/**
 * TicketService
 * 
 * @see Implementazione => com.backend.service.impl.TicketServiceImpl
 */
@Service
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

  /**
   * Restituisce una lista di ticket in base agli id forniti.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#getTicketsByIds
   * @param ids
   * @return List<TicketResponseDto> con i dettagli dei ticket richiesti.
   */
  List<TicketResponseDto> getTicketsByIds(String ids);

  /**
   * Aggiorna lo stato di un ticket in base all'id fornito.
   * 
   * @see implementazione =>
   *      com.backend.service.impl.TicketServiceImpl#updateTicketStatus
   * @param id
   * @param status
   * @return boolean
   */
  TicketResponseDto updateTicketStatus(Long id, String status);

}

package com.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.models.dtos.request.TicketRequestDto;
import com.app.models.dtos.response.TicketResponseDto;

/**
 * TicketService
 * 
 * @see Implementazione => service.impl.TicketServiceImpl
 */
@Service
public interface TicketService {

  /**
   * Crea un nuovo ticket nel database con i dettagli forniti nel
   * ticketRequestDto.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#createTicket
   * @param ticketRequestDto
   * @return TicketResponseDto con i dettagli del ticket appena creato.
   */
  TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);

  /**
   * Restituisce un ticket dal database in base all'id fornito.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#getTicketById
   * @param id
   * @return TicketResponseDto con i dettagli del ticket richiesto.
   */
  TicketResponseDto getTicketById(Long id);

  /**
   * Restituisce tutti i ticket presenti nel database.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#getAllTickets
   * @return List<TicketResponseDto> con i dettagli di tutti i ticket presenti nel
   *         database.
   */
  List<TicketResponseDto> getAllTickets();

  /**
   * Aggiorna un ticket nel database con i dettagli forniti nel ticketRequestDto.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#updateTicket
   * @param id
   * @param ticketRequestDto
   * @return TicketResponseDto con i dettagli del ticket appena aggiornato.
   */
  TicketResponseDto updateTicket(Long id, TicketRequestDto ticketRequestDto);

  /**
   * Elimina un ticket dal database in base all'id fornito.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#deleteTicket
   * @param id
   * @return void
   */
  void deleteTicket(Long id);

  /**
   * Restituisce una lista di ticket in base agli id forniti.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#getTicketsByIds
   * @param ids
   * @return List<TicketResponseDto> con i dettagli dei ticket richiesti.
   */
  List<TicketResponseDto> getTicketsByIds(String ids);

  /**
   * Aggiorna lo stato di un ticket in base all'id fornito.
   * 
   * @see implementazione =>
   *      service.impl.TicketServiceImpl#updateTicketStatus
   * @param id
   * @param status
   * @return boolean
   */
  TicketResponseDto updateTicketStatus(Long id, String status);

}

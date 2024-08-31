package com.backend.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.model.DTO.request.TicketRequestDto;
import com.backend.model.DTO.response.AccountResponseDTO;
import com.backend.model.DTO.response.TicketResponseDto;
import com.backend.model.Enum.TicketStatus;
import com.backend.model.Ticket;
import com.backend.repository.AccountRepository;
import com.backend.repository.TicketRepository;
import com.backend.service.AccountService;
import com.backend.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

  /**
   * Dependency injections
   */
  private final TicketRepository ticketRepository;

  private final AccountRepository accountRepository;

  private final AccountService accountService;

  /**
   * Costruttore
   */
  public TicketServiceImpl(TicketRepository ticketRepository, AccountRepository accountRepository,
      AccountService accountService) {
    this.ticketRepository = ticketRepository;
    this.accountRepository = accountRepository;
    this.accountService = accountService;
  }

  /**
   * Crea un nuovo ticket nel database con i dettagli forniti nel
   * ticketRequestDto.
   *
   * @param ticketRequestDto Dto che contiene i dettagli del ticket da creare.
   * @return TicketResponseDto Dto che contiene i dettagli del ticket appena
   *         creato.
   * @throws RuntimeException se l'account con il quale si vuole creare il ticket
   *                          non esiste.
   */
  @Override
  public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
    Account account = accountRepository.findById(ticketRequestDto.getAccountId())
        .orElseThrow(() -> new RuntimeException("Account not found"));

    Ticket ticket = new Ticket();
    ticket.setTitle(ticketRequestDto.getTitle());
    ticket.setDescription(ticketRequestDto.getDescription());
    ticket.setStatus(ticketRequestDto.getStatus());
    ticket.setAccount(account);
    ticket.setCreatedAt(ticketRequestDto.getCreatedAt());
    ticket.setUpdatedAt(ticketRequestDto.getUpdatedAt());

    Ticket savedTicket = ticketRepository.save(ticket);
    return mapToDto(savedTicket);
  }

  /**
   * Ottiene un ticket dal database in base all'id fornito e lo mappa in un
   * TicketResponseDto.
   * 
   * @param id l'id del ticket da ottenere
   * @return un TicketResponseDto che contiene i dettagli del ticket richiesto
   * @throws RuntimeException se il ticket non esiste
   */
  @Override
  public TicketResponseDto getTicketById(Long id) {
    Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Ticket not found"));
    return mapToDto(ticket);
  }

  /**
   * Ottiene tutti i ticket presenti nel database, dopodiché mappa ogni ticket in
   * un
   * TicketResponseDto e li restituisce come una lista.
   *
   * @return una lista di TicketResponseDto che contiene i dettagli di tutti i
   *         ticket
   */
  @Override
  public List<TicketResponseDto> getAllTickets() {
    List<Ticket> tickets = ticketRepository.findAll();
    return tickets.stream().map(this::mapToDto).collect(Collectors.toList());
  }

  /**
   * aggiorna un ticket esistente con i dettagli forniti nel ticketRequestDto.
   * 
   * @param id               l'id del ticket da aggiornare.
   * @param ticketRequestDto i nuovi dettagli del ticket.
   * @return un TicketResponseDto che contiene i dettagli del ticket aggiornato.
   * @throws RuntimeException se il ticket non esiste.
   */
  @Override
  public TicketResponseDto updateTicket(Long id, TicketRequestDto ticketRequestDto) {
    Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Ticket not found"));

    ticket.setTitle(ticketRequestDto.getTitle());
    ticket.setDescription(ticketRequestDto.getDescription());
    ticket.setStatus(ticketRequestDto.getStatus());
    ticket.setUpdatedAt(ticketRequestDto.getUpdatedAt());

    Ticket updatedTicket = ticketRepository.save(ticket);
    return mapToDto(updatedTicket);
  }

  /**
   * Cancella un ticket dal database in base all'id fornito.
   *
   * @param id l'id del ticket da cancellare
   */
  @Override
  public void deleteTicket(Long id) {
    ticketRepository.deleteById(id);
  }

  /**
   * Ottiene una lista di ticket in base agli id forniti in formato stringa,
   * divisi da virgole. Dopodiché mappa ogni ticket in un TicketResponseDto e li
   * restituisce come una lista.
   *
   * @param ids una stringa di id separati da virgole
   * @return una lista di TicketResponseDto che contiene i dettagli dei ticket
   *         richiesti
   */
  @Override
  public List<TicketResponseDto> getTicketsByIds(String ids) {
    List<Long> ticketIds = Arrays.stream(ids.split(","))
        .map(Long::parseLong)
        .collect(Collectors.toList());

    List<TicketResponseDto> tickets = new ArrayList<>();
    for (Long ticketId : ticketIds) {
      tickets.add(getTicketById(ticketId));
    }
    return tickets;
  }

  /**
   * Mappa un Ticket in un TicketResponseDto.
   *
   * @param ticket Il ticket da mappare.
   * @return un TicketResponseDto che contiene i dettagli del ticket.
   */
  private TicketResponseDto mapToDto(Ticket ticket) {
    TicketResponseDto dto = new TicketResponseDto();
    dto.setId(ticket.getId());
    dto.setTitle(ticket.getTitle());
    dto.setDescription(ticket.getDescription());
    dto.setStatus(ticket.getStatus());
    dto.setCreatedAt(ticket.getCreatedAt());
    dto.setUpdatedAt(ticket.getUpdatedAt());

    Long idAccount = ticket.getAccount().getId();
    Account account = accountRepository.findById(idAccount)
        .orElseThrow(() -> new RuntimeException("Account not found"));
    AccountResponseDTO accountDto = accountService.convertToDTO(account);
    dto.setAccount(accountDto);
    return dto;
  }

  /**
   * Converte un ticket in un TicketResponseDto.
   * 
   * @param ticket Il ticket da convertire.
   * @return un TicketResponseDto che contiene i dettagli del ticket.
   */
  private TicketResponseDto convertToDTO(Ticket ticket) {
    TicketResponseDto dto = new TicketResponseDto();
    dto.setId(ticket.getId());
    dto.setTitle(ticket.getTitle());
    dto.setDescription(ticket.getDescription());
    dto.setStatus(ticket.getStatus());
    dto.setCreatedAt(ticket.getCreatedAt());
    dto.setUpdatedAt(ticket.getUpdatedAt());
    dto.setAccount(accountService.convertToDTO(ticket.getAccount()));
    return dto;
  }

  /**
   * Aggiorna lo stato di un ticket nel database.
   *
   * @param id     l'id del ticket da aggiornare
   * @param status il nuovo stato del ticket
   * @return true se l'aggiornamento è andato a buon fine, false altrimenti
   */
  @Override
  public TicketResponseDto updateTicketStatus(Long id, String status) {
    try {
      Optional<Ticket> optionalTicket = ticketRepository.findById(id);
      if (optionalTicket.isEmpty()) {
        System.out.println("Ticket non trovato");
        return null;
      }
      Ticket ticket = optionalTicket.get();
      Account account = ticket.getAccount();
      TicketStatus ticketStatus;
      ticketStatus = TicketStatus.valueOf(status.toUpperCase());
      ticket.setStatus(ticketStatus);
      ticket.setAccount(account);

      ticketRepository.save(ticket);
      return convertToDTO(ticket);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
}

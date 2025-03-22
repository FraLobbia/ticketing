package com.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.models.entities.Ticket;
import com.app.models.enums.TicketStatusEnum;
import com.app.repositories.TicketRepository;
import com.app.services.interfaces.BaseCrudService;
import com.authentication.models.entities.Account;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TicketService implements BaseCrudService<Ticket, Long> {

	@Autowired
	private TicketRepository repo;

	@Autowired
	private AccountService accountService;

	@Override
	public List<Ticket> readAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Ticket> read(Long id) {
		Ticket ticket = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("[read] Ticket con id (" + id + ") non trovato nel db"));
		return Optional.of(ticket);
	}

	@Override
	public Ticket create(Ticket e) {
		Account account = accountService.read(e.getAccount().getId())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		Ticket ticket = new Ticket();
		ticket.setTitle(e.getTitle());
		ticket.setDescription(e.getDescription());
		ticket.setStatus(TicketStatusEnum.OPEN);
		ticket.setAccount(account);
		ticket.setCreatedAt(e.getCreatedAt());
		ticket.setUpdatedAt(e.getUpdatedAt());

		Ticket savedTicket = repo.save(ticket);
		return savedTicket;
	}

	@Override
	public Ticket update(Ticket e) {
		Ticket ticket = repo.findById(e.getId()).orElseThrow(
				() -> new RuntimeException("[updateTicket] Ticket con id (" + e.getId() + ") non trovato nel db"));

		ticket.setTitle(e.getTitle());
		ticket.setDescription(e.getDescription());
		ticket.setStatus(e.getStatus());
		ticket.setUpdatedAt(e.getUpdatedAt());

		Ticket updatedTicket = repo.save(ticket);
		return updatedTicket;
	}

	@Override
	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("[delete] Errore durante la cancellazione del ticket con id (" + id + ")");
		}
	}

	/**
	 * Riceve una stringa di ID di ticket separati da virgola e restituisce una
	 * lista di ticket.
	 * 
	 * @param ids
	 * @return
	 */
	public List<Ticket> readAllByIds(String ids) {
		List<Long> ticketIDs = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());

		List<Ticket> tickets = repo.findAllById(ticketIDs);
		return tickets;
	}

	/**
	 * Aggiorna lo stato di un ticket nel database.
	 *
	 * @param id     l'id del ticket da aggiornare
	 * @param status il nuovo stato del ticket
	 * @return true se l'aggiornamento è andato a buon fine, false altrimenti
	 */
	public Ticket updateTicketStatus(Long id, String status) {

		Optional<Ticket> optionalTicket = Optional.ofNullable(repo.findById(id).orElseThrow(() -> new RuntimeException(
				"[updateTicketStatus] Non è stato possibile trovare il ticket con id (" + id + ")")));

		Ticket ticket = optionalTicket.get();
		Account account = ticket.getAccount();
		TicketStatusEnum ticketStatus;
		ticketStatus = TicketStatusEnum.valueOf(status.toUpperCase());
		ticket.setStatus(ticketStatus);
		ticket.setAccount(account);

		Ticket updatedTicket = repo.save(ticket);

		return updatedTicket;

	}

}

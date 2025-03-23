package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.entities.Comment;
import com.app.repository.CommentRepository;
import com.authentication.models.entities.Account;
import com.authentication.services.AccountService;
import com.common.interfaces.BaseCrudService;

@Service
public class CommentService implements BaseCrudService<Comment, Long> {

	@Autowired
	private CommentRepository repo;

	@Autowired
	private AccountService accountService;

	@Override
	public List<Comment> readAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Comment> read(Long id) {
		Comment Comment = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("[read] Comment con id (" + id + ") non trovato nel db"));
		return Optional.of(Comment);
	}

	@Override
	public Comment save(Comment e) {
		Account account = accountService.read(e.getAccount().getId())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		Comment comment = new Comment();
		comment.setContent(e.getContent());
		comment.setTicket(e.getTicket());
		comment.setAccount(account);
		comment.setCreatedAt(LocalDateTime.now());

		Comment savedComment = repo.save(comment);
		return savedComment;
	}

	@Override
	public Comment update(Comment e) {
		Account account = accountService.read(e.getAccount().getId())
				.orElseThrow(() -> new RuntimeException("Account not found"));

		Comment comment = repo.findById(e.getId()).orElseThrow(
				() -> new RuntimeException("[updateComment] Comment con id (" + e.getId() + ") non trovato nel db"));

		comment.setContent(e.getContent());
		comment.setTicket(e.getTicket());
		comment.setAccount(account);
		comment.setUpdatedAt(LocalDateTime.now());

		Comment updatedComment = repo.save(comment);
		return updatedComment;
	}

	@Override
	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("[delete] Errore durante la cancellazione del Comment con id (" + id + ")");
		}
	}

	/**
	 * Restituisce i commenti associati ad un Ticket.
	 * 
	 * @param id - L'id del Ticket.
	 * @return La lista dei commenti associati al ticket.
	 */
	public List<Comment> readAllCommentsPerTicket(Long id) {
		return repo.findByTicketId(id);
	}
}

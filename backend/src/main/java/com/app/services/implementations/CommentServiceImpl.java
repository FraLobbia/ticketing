package com.app.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.models.dtos.request.CommentRequestDTO;
import com.app.models.dtos.response.CommentResponseDTO;
import com.app.models.entities.Comment;
import com.app.models.entities.Ticket;
import com.app.repositories.CommentRepository;
import com.app.repositories.TicketRepository;
import com.app.services.CommentService;
import com.authentication.models.dto.AccountDTO;
import com.authentication.models.entities.Account;
import com.authentication.repositories.AccountRepository;

@Service
public class CommentServiceImpl implements CommentService {

  /**
   * Dependency injections
   */
  private final CommentRepository commentRepository;

  private final AccountRepository accountRepository;

  private final TicketRepository ticketRepository;

  /**
   * Costruttore
   */
  public CommentServiceImpl(CommentRepository commentRepository, AccountRepository accountRepository,
      TicketRepository ticketRepository) {
    this.commentRepository = commentRepository;
    this.accountRepository = accountRepository;
    this.ticketRepository = ticketRepository;
  }

  /**
   * Crea un commento associato ad un account e ad un ticket.
   * 
   * @param commentRequestDTO Il commento da creare in formato DTO request.
   * @return Il commento creato in formato DTO response.
   * @throws RuntimeException se l'account o il ticket non esistono.
   */
  @Override
  public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
    Comment comment = new Comment();
    comment.setContent(commentRequestDTO.getContent());
    comment.setCreatedAt(commentRequestDTO.getCreatedAt());
    Account account = accountRepository.findById(commentRequestDTO.getAccountId())
        .orElseThrow(() -> new RuntimeException("Account not found"));
    Ticket ticket = this.ticketRepository.findById(commentRequestDTO.getTicketId())
        .orElseThrow(() -> new RuntimeException("Ticket not found"));
    comment.setAccount(account);
    comment.setTicket(ticket);
    commentRepository.save(comment);
    CommentResponseDTO commentResponseDTO = CommentResponseDTO.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .author(AccountDTO.builder()
            .id(account.getId())
            .name(account.getName())
            .surname(account.getSurname())
            .email(account.getEmail())
            .build())
        .build();
    return commentResponseDTO;
  }

  /**
   * Restituisce i commenti associati ad un ticket.
   * 
   * @param idTicket L'id del ticket.
   * @return La lista dei commenti associati al ticket in formato DTO response.
   */
  @Override
  public List<CommentResponseDTO> getCommentsByTicketId(Long idTicket) {
    List<Comment> comments = commentRepository.findByTicketId(idTicket);
    List<CommentResponseDTO> commentResponseDTOs = toDto(comments);
    return commentResponseDTOs;
  }

  /**
   * Aggiorna un commento.
   * 
   * @param id                                 L'id del commento da aggiornare.
   * @param commentRequestDTOCommentRequestDTO Il commento aggiornato in formato
   *                                           DTO request.
   * @return Il commento aggiornato in formato DTO response.
   */
  @Override
  public CommentResponseDTO updateComment(Long id, CommentRequestDTO commentRequestDTOCommentRequestDTO) {
    Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

    comment.setContent(commentRequestDTOCommentRequestDTO.getContent());
    comment.setCreatedAt(commentRequestDTOCommentRequestDTO.getCreatedAt());
    commentRepository.save(comment);
    CommentResponseDTO commentResponseDTO = CommentResponseDTO.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .author(AccountDTO.builder()
            .id(comment.getAccount().getId())
            .name(comment.getAccount().getName())
            .surname(comment.getAccount().getSurname())
            .email(comment.getAccount().getEmail())
            .build())
        .build();
    return commentResponseDTO;
  }

  /**
   * Elimina un commento.
   * 
   * @param id L'id del commento da eliminare.
   */
  @Override
  public void deleteComment(Long id) {
    commentRepository.deleteById(id);
  }

  /**
   * Metodo di conversione da Comment a DTO
   * 
   * @param comment
   * @return
   */
  private static CommentResponseDTO toDto(Comment comment) {
    return CommentResponseDTO.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .author(AccountDTO.builder()
            .id(comment.getAccount().getId())
            .name(comment.getAccount().getName())
            .surname(comment.getAccount().getSurname())
            .email(comment.getAccount().getEmail())
            .build())
        .build();
  }

  /**
   * Metodo di conversione da lista di Comment a lista di DTO
   * 
   * @param List<Comment>
   * @return List<CommentResponseDTO>
   */
  private static List<CommentResponseDTO> toDto(List<Comment> comments) {
    return comments.stream()
        .map(CommentServiceImpl::toDto)
        .collect(Collectors.toList());
  }

}

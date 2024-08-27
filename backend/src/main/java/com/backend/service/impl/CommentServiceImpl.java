package com.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.model.Comment;
import com.backend.model.DTO.request.CommentRequestDTO;
import com.backend.model.DTO.response.AccountResponseDTO;
import com.backend.model.DTO.response.CommentResponseDTO;
import com.backend.model.Ticket;
import com.backend.repository.AccountRepository;
import com.backend.repository.CommentRepository;
import com.backend.repository.TicketRepository;
import com.backend.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TicketRepository ticketRepository;

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
        .author(AccountResponseDTO.builder()
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
        .author(AccountResponseDTO.builder()
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
        .author(AccountResponseDTO.builder()
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

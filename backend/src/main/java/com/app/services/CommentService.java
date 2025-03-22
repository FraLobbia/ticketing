package com.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.models.dtos.CommentDto;
import com.app.models.entities.Comment;
import com.app.repositories.CommentRepository;
import com.app.repositories.TicketRepository;
import com.authentication.models.dto.AccountDTO;
import com.authentication.repositories.AccountRepository;

@Service
public class CommentService {
  /**
   * Crea un commento associato ad un account e ad un ticket.
   * 
   * @param commentRequestDTO Il commento da creare in formato DTO request.
   * @return Il commento creato in formato DTO response.
   * @throws RuntimeException se l'account o il ticket non esistono.
//   */
//  CommentDto createComment(CommentRequestDTO commentRequestDTO);
//
//  /**
//   * Restituisce i commenti associati ad un ticket.
//   * 
//   * @param idTicket L'id del ticket.
//   * @return La lista dei commenti associati al ticket in formato DTO response.
//   */
//  List<CommentDto> getCommentsByTicketId(Long idTicket);
//
//  /**
//   * Aggiorna un commento.
//   * 
//   * @param id                                 L'id del commento da aggiornare.
//   * @param commentRequestDTOCommentRequestDTO Il commento aggiornato in formato
//   *                                           DTO request.
//   * @return Il commento aggiornato in formato DTO response.
//   */
//  CommentDto updateComment(Long id, CommentRequestDTO commentRequestDTO);
  


//  /**
//   * Crea un commento associato ad un account e ad un ticket.
//   * 
//   * @param CommentDto Il commento da creare in formato DTO request.
//   * @return Il commento creato in formato DTO response.
//   * @throws RuntimeException se l'account o il ticket non esistono.
//   */
//  @Override
//  public CommentDto createComment(CommentDto CommentDto) {
//    Comment comment = new Comment();
//    comment.setContent(CommentDto.getContent());
//    comment.setCreatedAt(CommentDto.getCreatedAt());
//    Account account = accountRepository.findById(CommentDto.getAccountId())
//        .orElseThrow(() -> new RuntimeException("Account not found"));
//    Ticket ticket = this.ticketRepository.findById(CommentDto.getTicketId())
//        .orElseThrow(() -> new RuntimeException("Ticket not found"));
//    comment.setAccount(account);
//    comment.setTicket(ticket);
//    commentRepository.save(comment);
//    CommentDto commentResponseDTO = CommentDto.builder()
//        .id(comment.getId())
//        .content(comment.getContent())
//        .createdAt(comment.getCreatedAt())
//        .author(AccountDTO.builder()
//            .id(account.getId())
//            .name(account.getName())
//            .surname(account.getSurname())
//            .email(account.getEmail())
//            .build())
//        .build();
//    return commentResponseDTO;
//  }
//
//  /**
//   * Restituisce i commenti associati ad un ticket.
//   * 
//   * @param idTicket L'id del ticket.
//   * @return La lista dei commenti associati al ticket in formato DTO response.
//   */
//  @Override
//  public List<CommentDto> getCommentsByTicketId(Long idTicket) {
//    List<Comment> comments = commentRepository.findByTicketId(idTicket);
//    List<CommentDto> commentResponseDTOs = toDto(comments);
//    return commentResponseDTOs;
//  }
//
//  /**
//   * Aggiorna un commento.
//   * 
//   * @param id                                 L'id del commento da aggiornare.
//   * @param CommentDtoCommentDto Il commento aggiornato in formato
//   *                                           DTO request.
//   * @return Il commento aggiornato in formato DTO response.
//   */
//  @Override
//  public CommentDto updateComment(Long id, CommentDto CommentDtoCommentDto) {
//    Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
//
//    comment.setContent(CommentDtoCommentDto.getContent());
//    comment.setCreatedAt(CommentDtoCommentDto.getCreatedAt());
//    commentRepository.save(comment);
//    CommentDto commentResponseDTO = CommentDto.builder()
//        .id(comment.getId())
//        .content(comment.getContent())
//        .createdAt(comment.getCreatedAt())
//        .author(AccountDTO.builder()
//            .id(comment.getAccount().getId())
//            .name(comment.getAccount().getName())
//            .surname(comment.getAccount().getSurname())
//            .email(comment.getAccount().getEmail())
//            .build())
//        .build();
//    return commentResponseDTO;
//  }

//  /**
//   * Elimina un commento.
//   * 
//   * @param id L'id del commento da eliminare.
//   */
//  @Override
//  public void deleteComment(Long id) {
//    repo.deleteById(id);
//  }




}

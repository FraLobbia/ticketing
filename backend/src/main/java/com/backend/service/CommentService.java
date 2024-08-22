package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.model.DTO.request.CommentRequestDTO;
import com.backend.model.DTO.response.CommentResponseDTO;

@Service
public interface CommentService {
  /**
   * Crea un commento associato ad un account e ad un ticket.
   * 
   * @param commentRequestDTO Il commento da creare in formato DTO request.
   * @return Il commento creato in formato DTO response.
   * @throws RuntimeException se l'account o il ticket non esistono.
   */
  CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO);

  /**
   * Restituisce i commenti associati ad un ticket.
   * 
   * @param idTicket L'id del ticket.
   * @return La lista dei commenti associati al ticket in formato DTO response.
   */
  List<CommentResponseDTO> getCommentsByTicketId(Long idTicket);

  /**
   * Aggiorna un commento.
   * 
   * @param id                                 L'id del commento da aggiornare.
   * @param commentRequestDTOCommentRequestDTO Il commento aggiornato in formato
   *                                           DTO request.
   * @return Il commento aggiornato in formato DTO response.
   */
  CommentResponseDTO updateComment(Long id, CommentRequestDTO commentRequestDTO);

  /**
   * Elimina un commento.
   * 
   * @param id L'id del commento da eliminare.
   */
  void deleteComment(Long id);
}

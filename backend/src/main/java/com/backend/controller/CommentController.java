package com.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.DTO.request.CommentRequestDTO;
import com.backend.model.DTO.response.CommentResponseDTO;
import com.backend.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  /**
   * Servizi iniettati
   */
  private final CommentService commentService;

  /**
   * Costruttore
   */
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping
  public CommentResponseDTO createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
    return commentService.createComment(commentRequestDTO);
  }

  @GetMapping("/ticket/{id}")
  public List<CommentResponseDTO> getCommentsByTicketId(@PathVariable Long id) {
    return commentService.getCommentsByTicketId(id);
  }

  @PutMapping("/{id}")
  public CommentResponseDTO updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO) {
    return commentService.updateComment(id, commentRequestDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
  }
}

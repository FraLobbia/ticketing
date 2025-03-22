package com.app.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.services.CommentService;

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

//  @PostMapping
//  public CommentDto createComment(@RequestBody CommentDto commentRequestDTO) {
//    return commentService.createComment(commentRequestDTO);
//  }
//
//  @GetMapping("/ticket/{id}")
//  public List<CommentDto> getCommentsByTicketId(@PathVariable Long id) {
//    return commentService.getCommentsByTicketId(id);
//  }
//
//  @PutMapping("/{id}")
//  public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentDto commentRequestDTO) {
//    return commentService.updateComment(id, commentRequestDTO);
//  }

//  @DeleteMapping("/{id}")
//  public void deleteComment(@PathVariable Long id) {
//    commentService.deleteComment(id);
//  }
}

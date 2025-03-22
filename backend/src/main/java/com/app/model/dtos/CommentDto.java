package com.app.model.dtos;

import java.time.LocalDateTime;

import com.authentication.models.dto.AccountDTO;

import lombok.Data;

@Data
public class CommentDto {
  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private AccountDTO author;
  private TicketDto ticket;
}

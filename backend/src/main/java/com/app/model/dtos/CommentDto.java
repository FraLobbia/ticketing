package com.app.model.dtos;

import java.time.LocalDateTime;

import com.authentication.models.dto.AccountDto;

import lombok.Data;

@Data
public class CommentDto {
  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private AccountDto author;
  private TicketDto ticket;
}

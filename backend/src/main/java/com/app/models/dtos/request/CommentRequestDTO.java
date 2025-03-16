package com.app.models.dtos.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDTO {
  private String content;
  private LocalDateTime createdAt;
  private Long ticketId;
  private Long accountId;
}

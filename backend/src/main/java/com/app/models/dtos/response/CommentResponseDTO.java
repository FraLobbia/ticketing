package com.app.models.dtos.response;

import java.time.LocalDateTime;

import com.authentication.models.dto.AccountDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponseDTO {
  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private AccountDTO author;

  private CommentResponseDTO(Builder builder) {
    this.id = builder.id;
    this.content = builder.content;
    this.createdAt = builder.createdAt;
    this.author = builder.author;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private AccountDTO author;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder author(AccountDTO author) {
      this.author = author;
      return this;
    }

    public CommentResponseDTO build() {
      return new CommentResponseDTO(this);
    }
  }
}

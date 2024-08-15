package com.backend.model.DTO;

import java.time.LocalDateTime;

import com.backend.model.Enum.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private Long accountId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

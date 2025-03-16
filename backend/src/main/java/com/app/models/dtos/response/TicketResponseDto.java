package com.app.models.dtos.response;

import java.time.LocalDateTime;

import com.app.models.enums.TicketStatusEnum;
import com.authentication.models.dto.AccountDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDto {
    private Long id;
    private String title;
    private String description;
    private TicketStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AccountDTO account;
}

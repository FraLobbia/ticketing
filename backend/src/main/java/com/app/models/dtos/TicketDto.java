package com.app.models.dtos;

import java.time.LocalDateTime;

import com.app.models.enums.TicketStatusEnum;
import com.authentication.models.dto.AccountDTO;

import lombok.Data;

@Data
public class TicketDto{
    private Long id;
    private String title;
    private String description;
    private TicketStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AccountDTO account;
}

package com.app.model.dtos;

import java.time.LocalDateTime;

import com.app.model.enums.TicketStatusEnum;
import com.authentication.models.dto.AccountDto;

import lombok.Data;

@Data
public class TicketDto{
    private Long id;
    private String title;
    private String description;
    private TicketStatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AccountDto author;
}

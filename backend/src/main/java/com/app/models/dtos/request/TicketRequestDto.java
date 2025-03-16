package com.app.models.dtos.request;

import java.time.LocalDateTime;

import com.app.models.enums.TicketStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDto {
    private String title;
    private String description;
    private TicketStatusEnum status;
    private Long accountId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

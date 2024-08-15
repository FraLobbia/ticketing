package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

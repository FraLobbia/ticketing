package com.app.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.models.dtos.TicketDto;
import com.app.models.entities.Ticket;
import com.app.models.mapper.TicketMapper;
import com.app.services.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

	@Autowired
	private TicketService service;

	@Autowired
	private TicketMapper ticketMapper;

	private TicketDto toDto(Ticket ticket) {
		return ticketMapper.toDto(ticket);
	}

	private Ticket toEntity(TicketDto dto) {
		return ticketMapper.toEntity(dto);
	}

	@PostMapping
	public ResponseEntity<TicketDto> create(@RequestBody TicketDto dto) {
		Ticket e = service.create(toEntity(dto));

		if (e == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.status(201).body(toDto(e));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TicketDto> read(@PathVariable Long id) {
		TicketDto e = toDto(service.read(id).orElse(null));

		if (e == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(e);
	}

	@GetMapping
	public ResponseEntity<List<TicketDto>> readAll() {
		List<TicketDto> list = service.readAll().stream().map(this::toDto).collect(Collectors.toList());

		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.ok(list);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TicketDto> update(@PathVariable Long id, @RequestBody TicketDto dto) {
		Ticket e = service.update(toEntity(dto));

		if (e == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(toDto(e));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/viewing-tickets")
	public ResponseEntity<List<TicketDto>> getViewingTickets(String ids) {
		List<TicketDto> list = service.readAllByIds(ids).stream().map(this::toDto)
				.collect(Collectors.toList());

		if (list == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(list);
	}

	@PutMapping("/status/{id}")
	public ResponseEntity<TicketDto> updateTicketStatus(@PathVariable Long id,
			@RequestBody Map<String, String> status) {
		String statusString = status.get("status");
		Ticket e = service.updateTicketStatus(id, statusString);
		
		if (e == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(toDto(e));
	}
}

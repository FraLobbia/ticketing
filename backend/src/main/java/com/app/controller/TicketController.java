package com.app.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.dtos.TicketDto;
import com.app.model.entities.Ticket;
import com.app.model.mapper.ApplicationDtoMapper;
import com.app.service.TicketService;
import com.app.service.interfaces.BaseCrudService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController extends AbstractCrudController<TicketDto, Ticket, Long> {

	@Autowired
	private TicketService service;

	@Autowired
	private ApplicationDtoMapper mapper;

	@Override
    protected BaseCrudService<Ticket, Long> getService() {
        return service;
    }

    @Override
    protected TicketDto toDto(Ticket e) {
        return mapper.toDto(e);
    }

    @Override
    protected Ticket toEntity(TicketDto dto) {
        return mapper.toEntity(dto);
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

package com.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.dtos.CommentDto;
import com.app.model.entities.Comment;
import com.app.model.mapper.ApplicationDtoMapper;
import com.app.service.CommentService;
import com.common.abstracts.AbstractCrudController;
import com.common.interfaces.BaseCrudService;

@RestController
@RequestMapping("/api/comments")
public class CommentController extends AbstractCrudController<CommentDto, Comment, Long> {

	@Autowired
	private CommentService service;
	
	@Autowired
	private ApplicationDtoMapper mapper;

	@Override
	protected BaseCrudService<Comment, Long> getService() {
		return service;
	}

	protected CommentDto toDto(Comment e) {
		return mapper.toDto(e);
	}

	protected Comment toEntity(CommentDto dto) {
		return mapper.toEntity(dto);
	}
	
	@GetMapping("/ticket/{id}")
	public ResponseEntity<List<CommentDto>> readAllCommentsPerTicket(@PathVariable Long id) {
		List<CommentDto> list = service.readAllCommentsPerTicket(id).stream().map(this::toDto)
				.collect(Collectors.toList());

		if (list == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(list);
	}

}

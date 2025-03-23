package com.common.abstracts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.common.interfaces.BaseCrudService;

public abstract class AbstractCrudController<DTO, ENTITY, ID> {

	protected abstract BaseCrudService<ENTITY, ID> getService();

	protected abstract DTO toDto(ENTITY e);

	protected abstract ENTITY toEntity(DTO dto);

	@PostMapping
	public ResponseEntity<DTO> create(@RequestBody DTO dto) {
		ENTITY entity = getService().save(toEntity(dto));
		return entity == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
				: ResponseEntity.status(HttpStatus.CREATED).body(toDto(entity));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DTO> read(@PathVariable ID id) {
		return getService().read(id).map(this::toDto).map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@GetMapping
	public ResponseEntity<List<DTO>> readAll() {
		List<DTO> list = getService().readAll().stream().map(this::toDto).collect(Collectors.toList());

		if (list.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(list);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto) {
		ENTITY entity = getService().update(toEntity(dto));
		return entity == null ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
				: ResponseEntity.ok(toDto(entity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable ID id) {
		getService().delete(id);
		return ResponseEntity.noContent().build();
	}
}

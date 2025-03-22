package com.app.service.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia base per i servizi CRUD.
 *
 * @param <ENTITY>           Il tipo dell'entità.
 * @param <ID>          Il tipo dell'identificativo dell'entità.
 */
public interface BaseCrudService<ENTITY, ID> {

	List<ENTITY> readAll();

	ENTITY create(ENTITY entity);

	Optional<ENTITY> read(ID id);

	ENTITY update(ENTITY entity);

	void delete(ID id);
}

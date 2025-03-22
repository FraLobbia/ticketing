package com.app.services.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia base per i servizi CRUD.
 *
 * @param <T>           Il tipo dell'entità.
 * @param <ID>          Il tipo dell'identificativo dell'entità.
 */
public interface BaseCrudService<T, ID> {

	List<T> readAll();

	T create(T entity);

	Optional<T> read(ID id);

	T update(T entity);

	void delete(ID id);
}

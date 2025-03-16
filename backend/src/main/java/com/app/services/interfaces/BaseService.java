package com.app.services.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia base per i servizi CRUD.
 *
 * @param <T>  Il tipo dell'entità.
 * @param <ID> Il tipo dell'identificativo dell'entità.
 * @param <DTOrequest> Il tipo del DTO per la richiesta dal client.
 * @param <DTOresponse> Il tipo del DTO per la risposta al client.
 */
public interface BaseService<T, ID, DTOrequest, DTOresponse> {

    /**
     * Restituisce la lista di tutte le entità.
     *
     * @return lista di entità
     */
    List<T> findAll();

    /**
     * Restituisce un'entità per l'id specificato.
     *
     * @param id identificativo dell'entità
     * @return Optional che contiene l'entità se presente, altrimenti vuoto
     */
    Optional<DTOresponse> findById(ID id);

    /**
     * Salva una nuova entità.
     *
     * @param il dto che contiene i dati dell'entità da salvare
     * @return il dto che contiene i dati dell'entità appena salvata
     */
    DTOresponse save(DTOrequest entity);

    /**
     * Aggiorna un'entità esistente.
     *
     * @param entity l'entità da aggiornare
     * @return l'entità aggiornata
     */
    T update(T entity);

    /**
     * Elimina l'entità identificata dal suo ID.
     *
     * @param id identificativo dell'entità da eliminare
     */
    void deleteById(ID id);

    /**
     * Elimina l'entità.
     *
     * @param entity l'entità da eliminare
     */
    void delete(T entity);
}


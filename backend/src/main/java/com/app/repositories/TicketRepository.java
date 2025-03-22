package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.models.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	@Query(value = "SELECT * FROM tickets WHERE id IN (:ids)", nativeQuery = true)
	List<Ticket> findAllByIds(@Param("ids") List<Long> ids);
}

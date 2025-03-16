package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.models.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByTicketId(Long idTicket);
}

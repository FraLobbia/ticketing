package com.backend.controller;

import com.backend.model.Attachment;
import com.backend.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

  @Autowired
  private AttachmentService attachmentService;

  @GetMapping
  public List<Attachment> getAllAttachments() {
    return attachmentService.getAllAttachments();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
    Optional<Attachment> attachment = attachmentService.getAttachmentById(id);
    return attachment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Attachment> createAttachment(
      @RequestParam("ticket_id") Long ticketId,
      @RequestParam("file") MultipartFile file) {
    try {
      Attachment savedAttachment = attachmentService.saveAttachment(ticketId, file);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedAttachment);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
    attachmentService.deleteAttachment(id);
    return ResponseEntity.noContent().build();
  }
}

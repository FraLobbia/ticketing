package com.backend.service;

import com.backend.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AttachmentService {

  List<Attachment> getAllAttachments();

  Optional<Attachment> getAttachmentById(Long id);

  Attachment saveAttachment(Long ticketId, MultipartFile file) throws IOException;

  void deleteAttachment(Long id);
}

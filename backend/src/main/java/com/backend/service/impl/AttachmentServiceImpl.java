package com.backend.service.impl;

import com.backend.model.Attachment;
import com.backend.model.Ticket;
import com.backend.repository.AttachmentRepository;
import com.backend.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

  @Autowired
  private AttachmentRepository attachmentRepository;

  @Override
  public List<Attachment> getAllAttachments() {
    return attachmentRepository.findAll();
  }

  @Override
  public Optional<Attachment> getAttachmentById(Long id) {
    return attachmentRepository.findById(id);
  }

  @Override
  public Attachment saveAttachment(Long ticketId, MultipartFile file) throws IOException {
    Attachment attachment = new Attachment();
    attachment.setTicket(new Ticket());
    attachment.getTicket().setId(ticketId);
    attachment.setFileName(file.getOriginalFilename());
    attachment.setFileType(file.getContentType());
    attachment.setData(file.getBytes());

    return attachmentRepository.save(attachment);
  }

  @Override
  public void deleteAttachment(Long id) {
    if (attachmentRepository.existsById(id)) {
      attachmentRepository.deleteById(id);
    }
  }
}

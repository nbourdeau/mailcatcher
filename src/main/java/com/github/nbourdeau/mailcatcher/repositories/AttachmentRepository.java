package com.github.nbourdeau.mailcatcher.repositories;

import com.github.nbourdeau.mailcatcher.model.Attachment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    List<Attachment> findByMessageId(Long messageId);

}

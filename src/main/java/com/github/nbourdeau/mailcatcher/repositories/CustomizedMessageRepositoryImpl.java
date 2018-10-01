package com.github.nbourdeau.mailcatcher.repositories;

import com.github.nbourdeau.mailcatcher.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class CustomizedMessageRepositoryImpl implements CustomizedMessageRepository {

    private final EntityManager entityManager;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public CustomizedMessageRepositoryImpl(EntityManager entityManager, AttachmentRepository attachmentRepository) {
        this.entityManager = entityManager;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    @Transactional
    public void safeDeleteAll() {
        // Delete by batches of ids
        Query query = entityManager.createQuery("SELECT id FROM Message m ORDER BY id").setMaxResults(1000);
        while (true) {
            List<Number> ids = query.getResultList();
            for (Number id : ids) {
                attachmentRepository.removeByMessageId(id.longValue());
                entityManager.remove(entityManager.find(Message.class, id.longValue()));
            }
            entityManager.flush();
            entityManager.clear();
            if (ids.size() < 1000)
                break;
        }
    }
}

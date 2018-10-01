package com.github.nbourdeau.mailcatcher;

import com.github.nbourdeau.mailcatcher.model.Attachment;
import com.github.nbourdeau.mailcatcher.model.Message;
import com.github.nbourdeau.mailcatcher.repositories.AttachmentRepository;
import com.github.nbourdeau.mailcatcher.repositories.MessageRepository;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

@Service
public class MessageService {

    private static Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void storeMessages(List<MimeMessage> mimeMessages) {
        for (MimeMessage mimeMessage : mimeMessages) {
            Message message = new Message();
            List<Attachment> attachments = new ArrayList<>();
            try {
                message.setTo(addressesToString(mimeMessage.getRecipients(javax.mail.Message.RecipientType.TO)));
                message.setDateTime(mimeMessage.getSentDate());
                message.setReplyTo(addressesToString(mimeMessage.getReplyTo()));
                message.setSubject(mimeMessage.getSubject());
                if (mimeMessage.getFrom() != null && mimeMessage.getFrom().length > 0)
                    message.setFrom(mimeMessage.getFrom()[0].toString());
                // Save all headers
                message.setHeaders(new HashMap<>());
                Enumeration<Header> headers = mimeMessage.getAllHeaders();
                while (headers.hasMoreElements()) {
                    Header header = headers.nextElement();
                    message.getHeaders().put(header.getName(), header.getValue());
                }
                // Parse body
                parsePart(mimeMessage, message, attachments);
            } catch (Exception e) {
                log.warn("Unable to store received message", e);
            }

            if (message.getBodyPlain() == null)
                message.setBodyPlain("");
            if (message.getBodyHtml() == null)
                message.setBodyHtml("");

            log.debug("Storing message from " + message.getFrom() + " to " + message.getTo());

            messageRepository.save(message);
            if (attachments.size() > 0) {
                for (Attachment attachment : attachments) {
                    attachment.setMessage(message);
                    attachmentRepository.save(attachment);
                }
            }
        }
    }

    private void parsePart(MimePart part, Message message, List<Attachment> attachments) throws MessagingException, IOException {
        if (part.isMimeType("text/html")) {
            message.setBodyHtml(part.getContent().toString());
        } else if (part.isMimeType("text/plain")) {
            message.setBodyPlain(part.getContent().toString());
        } else if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                parsePart((MimePart) mp.getBodyPart(i), message, attachments);
            }
        } else {
            // We consider this is an attached file here (inline or not)
            Attachment attachment = new Attachment();
            String ct = part.getContentType();
            if (ct.indexOf(";") > 0)
                ct = ct.substring(0, ct.indexOf(";"));
            attachment.setContentType(ct);
            attachment.setName(part.getFileName());
            attachment.setCid(part.getHeader("Content-ID", null));
            byte[] data = IOUtils.toByteArray(part.getInputStream());
            attachment.setContent(((Session) entityManager.getDelegate()).getLobHelper().createBlob(data));
            attachments.add(attachment);
        }
    }

    private String addressesToString(Address[] addresses) {
        if (addresses == null || addresses.length < 1)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Address address : addresses) {
            if (sb.length() > 0)
                sb.append(", ");
            sb.append(address.toString());
        }
        return sb.toString();
    }
}

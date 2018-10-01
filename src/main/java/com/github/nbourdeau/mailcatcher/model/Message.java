package com.github.nbourdeau.mailcatcher.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private Date dateTime;
    private String subject;
    private String to;
    @Column(name = "from_email") // from is not a valide column name ....
    private String from;
    private String replyTo;
    @Lob
    private String bodyPlain;
    @Lob
    private String bodyHtml;
    @ElementCollection
    private Map<String, String> headers;
    @Formula("(SELECT COUNT(id) FROM attachment a WHERE a.message_id = id)")
    @Column(insertable = false, updatable = false)
    private boolean hasAttachments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getBodyPlain() {
        return bodyPlain;
    }

    public void setBodyPlain(String bodyPlain) {
        this.bodyPlain = bodyPlain;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }
}

package com.github.nbourdeau.mailcatcher.model;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Attachment {

    @Id
    @GeneratedValue
    private Long id;
    private String contentType;
    private String name;
    private String cid;
    @Lob
    private Blob content;
    @ManyToOne
    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
        this.content = content;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

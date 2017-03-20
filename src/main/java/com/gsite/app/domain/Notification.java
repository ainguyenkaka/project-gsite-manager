package com.gsite.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("title")
    private String title;

    @NotNull
    @Size(min = 6)
    @Field("content")
    private String content;

    @Field("is_read")
    private Boolean isRead;

    @Field("created")
    private ZonedDateTime created;

    @Field("sent_users")
    private List<String> sentUsers = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Notification content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isIsRead() {
        return isRead;
    }

    public Notification isRead(Boolean isRead) {
        this.isRead = isRead;
        return this;
    }

    public List<String> getSentUsers() {
        return sentUsers;
    }

    public void setSentUsers(List<String> sentUsers) {
        this.sentUsers = sentUsers;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Notification created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        if (notification.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, notification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", content='" + content + "'" +
            ", isRead='" + isRead + "'" +
            ", created='" + created + "'" +
            '}';
    }
}

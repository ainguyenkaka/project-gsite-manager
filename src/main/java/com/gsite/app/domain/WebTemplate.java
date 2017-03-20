package com.gsite.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@Document(collection = "web_template")
public class WebTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 3)
    @Field("source")
    private String source;

    @NotNull
    @Min(1)
    @Field("rating")
    private int rating;

    @NotNull
    @Size(min = 3)
    @Field("category")
    private String category;

    @NotNull
    @Min(0)
    @Field("price")
    private BigDecimal price;

    @Field("image")
    private String image;

    @NotNull
    @Field("created")
    private ZonedDateTime created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WebTemplate() {
    }

    public WebTemplate name(String name) {
        this.name = name;
        this.created = ZonedDateTime.now();
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public WebTemplate source(String source) {
        this.source = source;
        this.created = ZonedDateTime.now();
        return this;
    }

    public WebTemplate(String name, String source, String category, String image) {
        this.name = name;
        this.source = source;
        this.category = category;
        this.image = image;
        this.created = ZonedDateTime.now();
        this.rating = 1;
        this.price = BigDecimal.ZERO;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getCreated() {
        return created;
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
        WebTemplate webTemplate = (WebTemplate) o;
        if (webTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, webTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WebTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", source='" + source + "'" +
            ", rating='" + rating + "'" +
            ", category='" + category + "'" +
            ", created='" + created + "'" +
            '}';
    }
}

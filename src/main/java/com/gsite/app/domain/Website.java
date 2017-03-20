package com.gsite.app.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "website")
public class Website implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("name")
    private String name;

    @NotNull
    @Field("created")
    private ZonedDateTime created;

    @NotNull
    @Field("user_id")
    private String user_id;

    @NotNull
    @Field("template")
    private String template;

    @NotNull
    @Size(min = 3)
    @Field("domain")
    private String domain;

    @Size(min = 3)
    @Field("des")
    private String des;

    @Field("custom")
    private WebCustomization custom;


    @Field("is_paid")
    private Boolean isPaid = false;

    @Field("shared_users")
    private List<String> sharedUsers;

    public Website() {
        this.custom = new WebCustomization();
        this.sharedUsers = new ArrayList<>();
    }


    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Website name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Website created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getUser_id() {
        return user_id;
    }

    public Website user_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Website template(String template) {
        this.template = template;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getDomain() {
        return domain;
    }

    public Website domain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public WebCustomization getCustom() {
        return custom;
    }

    public void setCustom(WebCustomization custom) {
        this.custom = custom;
    }

    public List<String> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<String> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Website website = (Website) o;
        if (website.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, website.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Website{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created='" + created + "'" +
            ", user_id='" + user_id + "'" +
            ", template='" + template + "'" +
            ", domain='" + domain + "'" +
            '}';
    }

    public class WebCustomization {

        // some basic customization

        public WebToolbar toolbar;
        public WebHomepage homepage;
        public WebSidenav sidenav;
        public WebFooter footer;
        public WebBasicInfo basicInfo;
        public WebSong song;
        public WebPhoto photo;

        public WebCustomization() {
        }

        public abstract class AbstractCustomWeb {
            public boolean isEnable = true;
            public AbstractCustomWeb(){}
        }

        public class WebToolbar extends AbstractCustomWeb {
            public String title;
            public String textColor;

            public WebToolbar() {
            }
        }

        public class WebHomepage extends AbstractCustomWeb{
            public String name;
            public String fullName;
            public String avatar;
            public String mainImage;

            public WebHomepage() {
            }
        }

        public class WebSidenav  extends AbstractCustomWeb{
            public String title;
            public String textColor;
            public String backgroundColor;

            public WebSidenav() {
            }
        }

        public class WebFooter extends AbstractCustomWeb {
            public WebFooter() {
            }
        }

        public class WebBasicInfo extends AbstractCustomWeb{
            public String firstName;
            public String lastName;
            public String email;
            public int age;
            public String des;

            public WebBasicInfo() {
            }
        }

        public class WebSong extends  AbstractCustomWeb{
            public List<SongItem> items;

            public WebSong() {

            }


        }

        public class WebPhoto extends AbstractCustomWeb {
            public List<PhotoItem> items;


            public WebPhoto() {

            }

        }
    }
}

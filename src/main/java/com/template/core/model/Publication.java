package com.template.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Publication extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    @OneToOne(cascade={CascadeType.PERSIST})
    private Cover cover;
    @Lob
    @Column(length = 20971520)
    private String content;
    @ManyToOne
    private Person author;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getAuthor() {
        return this.author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Cover getCover() {
        return this.cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
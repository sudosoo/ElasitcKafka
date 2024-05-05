package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

@Document(indexName = "es_post")
public class EsPost {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Boolean)
    private boolean isDeleted = false;

    @Field(type = Date,format= DateFormat.basic_date, pattern = "yyyy-MM-dd")
    private LocalDate deletedAt = LocalDate.of(9999, 12, 31);

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Keyword)
    private String writerName;

    @Field(type = FieldType.Integer)
    private int viewCount = 0;

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDate.now();
    }

    public EsPost() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setDeletedAt(LocalDate deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public LocalDate getDeletedAt() {
        return deletedAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getWriterName() {
        return writerName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public EsPost(Long id, String title, String content, boolean isDeleted, LocalDate deletedAt, Long categoryId, String writerName, int viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.categoryId = categoryId;
        this.writerName = writerName;
        this.viewCount = viewCount;
    }
}

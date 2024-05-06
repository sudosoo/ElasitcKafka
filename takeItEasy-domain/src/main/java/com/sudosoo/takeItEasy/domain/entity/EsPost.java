package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.WriteTypeHint;

@Document(indexName = "es_post",createIndex = false, writeTypeHint = WriteTypeHint.FALSE)
public class EsPost {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Keyword)
    private String writerName;

    @Field(type = FieldType.Integer)
    private int viewCount = 0;

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


    public Long getCategoryId() {
        return categoryId;
    }

    public String getWriterName() {
        return writerName;
    }

    public int getViewCount() {
        return viewCount;
    }


    public EsPost(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.categoryId = post.getCategory().getId();
        this.writerName = post.getWriter();
        this.viewCount = post.getViewCount();
    }
}

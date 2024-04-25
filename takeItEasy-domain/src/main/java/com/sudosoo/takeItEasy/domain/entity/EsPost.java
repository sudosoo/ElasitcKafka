package com.sudosoo.takeItEasy.domain.entity;

import com.sudosoo.takeItEasy.domain.support.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "es_post")
public class EsPost extends BaseEntity {
    @Id
    @Field(name = "es_post_id", type = FieldType.Long)
    private Long id;
    @Field(name = "title", type = FieldType.Text)
    private String title;

    @Field(name = "content", type = FieldType.Text)
    private String content;

    @Field(name = "is_deleted", type = FieldType.Boolean)
    private boolean isDeleted = false;

    @Field(name = "deleted_at", type = FieldType.Date)
    private LocalDate deletedAt = LocalDate.of(9999, 12, 31);

    @Field(name = "category_id", type = FieldType.Long)
    private Long categoryId;
    @Field(name = "member_id", type = FieldType.Long)
    private Long memberId;
    @Field(name = "writer_name", type = FieldType.Text)
    private String writerName;

    @Column(name = "view_count", nullable = false)
    @Field(name = "view_count", type = FieldType.Integer)
    private int viewCount = 0;

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDate.now();
    }

    public EsPost() {
    }

    public EsPost(String title, String content, Long categoryId, Long memberId, String writerName) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.memberId = memberId;
        this.writerName = writerName;
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

    public Long getMemberId() {
        return memberId;
    }

    public String getWriterName() {
        return writerName;
    }

    public int getViewCount() {
        return viewCount;
    }
}

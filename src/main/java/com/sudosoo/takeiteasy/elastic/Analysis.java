package com.sudosoo.takeiteasy.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Document(indexName = "#{elasticsearchIndex}")
public class Analysis {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Keyword)
    private String searchKeyword;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type = FieldType.Long)
    private int count;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate reg_dt;
}
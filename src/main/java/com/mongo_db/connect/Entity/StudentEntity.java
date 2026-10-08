package com.mongo_db.connect.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "student_tb")
public class StudentEntity {
    @Id
    private String id;
    private String name;
    private Integer age;
}

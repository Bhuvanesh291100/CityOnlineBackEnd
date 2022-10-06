package com.city.online.api.model.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "quiz")
public class Question {
    private String questionStatement;
    private Integer questionNumber;
    private Integer questionWeightage;
    private String correctOption;
    private String correctAnswer;
}

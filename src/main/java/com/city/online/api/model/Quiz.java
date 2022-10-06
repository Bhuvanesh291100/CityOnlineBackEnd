package com.city.online.api.model;

import com.city.online.api.dto.request.QuestionDto;
import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.enums.Status;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quiz")
@Builder
public class Quiz extends MongoAuditBaseEntity implements Serializable {
    @Id
    private String id;
    private String quizName;
    private String quizDate;
    private String quizDescription;
    private String quizQualification;
    private String startTime;
    private String endTime;
    private Integer quizMarks;
    private Integer quizTotalQuestion;
    private String  quizType; //subjective or objective
    private String quizSubject;
    private Status quizStatus;//created or finished
    private Integer quizScore;
    private List<QuestionDto> questions = new ArrayList<>();
}

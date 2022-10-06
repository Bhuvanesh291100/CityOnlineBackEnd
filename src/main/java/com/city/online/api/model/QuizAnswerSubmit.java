package com.city.online.api.model;

import com.city.online.api.dto.request.QuestionAndOptionDto;
import com.city.online.api.model.base.MongoAuditBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@Document(collection = "quizUserAnswer")
public class QuizAnswerSubmit extends MongoAuditBaseEntity implements Serializable {
    @Id
    private String Id;
    private String username;
    private String quizId;
    private String quizName;
    private Integer timeTakenToSubmit;
    private Integer score;
    List<QuestionAndOptionDto> questionAndOption = new ArrayList<>();
}

package com.city.online.api.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class QuestionAndOptionDto implements Serializable {
    private Integer questionNumber;
    private String questionStatement;
    //List in case of the questions which has more than 1 correct answers
    private List<AnswerResponseDto> answer = new ArrayList<>();
}


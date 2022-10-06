package com.city.online.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
public class QuestionDto {
    private String questionStatement;
    private Integer questionNumber;
    private Integer questionWeightage;
    private String correctOption;
    private String correctAnswer;
    private List<OptionDto> options = new ArrayList<>();


}

package com.city.online.api.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AnswerResponseDto implements Serializable {
    private String answerOptionSelected;
    private String answerSelected;
    private String correctAnswerOption;
    private String correctAnswer;
    private boolean isAnswerCorrect;
}

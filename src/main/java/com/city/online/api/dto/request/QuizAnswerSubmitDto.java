package com.city.online.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuizAnswerSubmitDto implements Serializable {
    @NotBlank
    @Size(min = 3, max = 20)
    public String username;
    @NotBlank
    @NotNull
    public String quizName;
    public String quizId;
    @NotBlank
    @NotNull
    @NotEmpty
    public Integer timeTakenToSubmit; // This time is in the seconds
    public Integer score;
    @NotBlank
    @NotNull
    @NotEmpty
    public List<QuestionAndOptionDto> questionAndOption = new ArrayList<>();
}

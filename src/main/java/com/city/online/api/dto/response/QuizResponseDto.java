package com.city.online.api.dto.response;

import com.city.online.api.dto.request.QuestionAndOptionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class QuizResponseDto {
    @Id
    private String quizId;
    @NotNull
    @NotBlank
    public String quizName;
    @NotNull
    @NotBlank
    public String quizDescription;
    @NotBlank
    @NotNull
    public String quizDate;
    @NotBlank
    @NotNull
    public String quizQualification;
    @NotNull
    @NotBlank
    public String startTime;
    @NotNull
    @NotBlank
    public String endTime;
    @NotNull
    @NotBlank
    public Integer quizMarks;
    @NotNull
    @NotBlank
    public Integer quizTotalQuestion;
    @NotNull
    @NotBlank
    public String quizType; //subjective or objective
    @NotNull
    @NotBlank
    public String quizSubject;
    @NotNull
    public String quizAttempt;
    private String quizStatus;
    @NotNull
    private Integer quizScore;
    @NotEmpty
    @NotNull
    @NotBlank
    public List<QuestionAndOptionDto> questionOption = new ArrayList<>();
}



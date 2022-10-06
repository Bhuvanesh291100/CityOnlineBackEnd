package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.OptionDto;
import com.city.online.api.dto.request.QuestionDto;
import com.city.online.api.dto.request.QuizRequestDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.repository.QuestionRepository;
import com.city.online.api.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Slf4j
public class QuizValidator implements Validator {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(@NotNull Object requestObject, @NotNull Errors errors) {

        QuizRequestDto quizRequestDto = (QuizRequestDto) requestObject;

        checkRequiredFieldsAreMissing(quizRequestDto);

        List<QuestionDto> questions = quizRequestDto.getQuestions();
        List<OptionDto> options = null;
        for (QuestionDto question : questions) {
            options = question.getOptions();
        }

        if (isQuestionFieldMissing(questions)) {
            throwException(AppConstants.QUESTION_FIELDS_MISSING_ERROR_CODE, AppConstants.QUESTION_FIELDS_MISSING_ERROR_MESSAGE);
        }

        if (isOptionFieldMissing(options)) {
            throwException(AppConstants.OPTION_FIELDS_MISSING_ERROR_CODE, AppConstants.OPTION_FIELDS_MISSING_ERROR_MESSAGE);
        }
    }

    private boolean isQuestionFieldMissing(List<QuestionDto> questions){
        for(QuestionDto subField: questions){
            if(subField.getQuestionStatement().isEmpty() || subField.getCorrectOption().isEmpty() || subField.getCorrectAnswer().isEmpty())
                return true;
        }
        return false;
    }

    private boolean isOptionFieldMissing(List<OptionDto> options){
        for(OptionDto subField: options){
            if(subField.getOptionValue().isEmpty() || subField.getOptionName().isEmpty())
                return true;
        }
        return false;
    }

    private void checkRequiredFieldsAreMissing(QuizRequestDto quizRequestDto) {
        if(quizRequestDto.getQuizName().isEmpty() || null == quizRequestDto.getQuizDate()
                || null == quizRequestDto.getQuizMarks() || quizRequestDto.getStartTime().isEmpty()
                || quizRequestDto.getEndTime().isEmpty() || quizRequestDto.getQuizQualification().isEmpty() || quizRequestDto.getQuizSubject().isEmpty()
                || null == quizRequestDto.getQuizTotalQuestion() || quizRequestDto.getQuizType().isEmpty()) {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE, AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE);
        }
    }

    private void throwException(String code, String errorMessage) {
        throw new BusinessException(code, errorMessage , HttpStatus.BAD_REQUEST, null);
    }
}

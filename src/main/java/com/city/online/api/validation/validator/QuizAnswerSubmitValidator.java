package com.city.online.api.validation.validator;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.AnswerResponseDto;
import com.city.online.api.dto.request.QuestionAndOptionDto;
import com.city.online.api.dto.request.QuizAnswerSubmitDto;
import com.city.online.api.exception.BusinessException;
import com.city.online.api.repository.QuizAnswerSubmitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class QuizAnswerSubmitValidator implements Validator {
    @Autowired
    QuizAnswerSubmitRepository quizAnswerSubmitRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(@NotNull Object requestObject, @NotNull Errors errors) {

        QuizAnswerSubmitDto quizAnswerSubmitDto = (QuizAnswerSubmitDto) requestObject;

        checkRequiredFieldsAreMissing(quizAnswerSubmitDto);

        checkIfAnswerAlreadySubmitted(quizAnswerSubmitDto);

        List<QuestionAndOptionDto> questionAndOption = quizAnswerSubmitDto.getQuestionAndOption();
        List<AnswerResponseDto> answers = null;
        for (QuestionAndOptionDto questions : questionAndOption) {
            answers = questions.getAnswer();
        }

        if (isQuestionFieldMissing(questionAndOption)) {
            throwException(AppConstants.QUESTION_FIELDS_MISSING_ERROR_CODE, AppConstants.QUESTION_FIELDS_MISSING_ERROR_MESSAGE);
        }

        if (isOptionFieldMissing(answers)){
            throwException(AppConstants.OPTION_FIELDS_MISSING_ERROR_CODE, AppConstants.OPTION_FIELDS_MISSING_ERROR_MESSAGE);
        }
    }

    private void checkIfAnswerAlreadySubmitted(QuizAnswerSubmitDto quizAnswerSubmitDto) {
        QuizAnswerSubmitDto quizAnswerSubmitDto1 = quizAnswerSubmitRepository.findByQuizIdAndUsername(quizAnswerSubmitDto.getQuizId(), quizAnswerSubmitDto.getUsername());
        if(Objects.nonNull(quizAnswerSubmitDto1)) {
            throwException(AppConstants.OPTION_SUBMIT_DUPLICATE_ERROR_CODE, AppConstants.OPTION_SUBMIT_DUPLICATE_ERROR_MESSAGE);
        }
    }

    private boolean isQuestionFieldMissing(List<QuestionAndOptionDto> questionAndOption){
        for(QuestionAndOptionDto subField: questionAndOption){
            if(subField.getQuestionStatement().isEmpty() || null == subField.getQuestionNumber() )
                return true;
        }
        return false;
    }

    private boolean isOptionFieldMissing(List<AnswerResponseDto> answerResponse){
        for(AnswerResponseDto subField: answerResponse){
            if(subField.getCorrectAnswer().isEmpty() || subField.getCorrectAnswerOption().isEmpty() || subField.getAnswerOptionSelected().isEmpty() || subField.getAnswerSelected().isEmpty())
                return true;
        }
        return false;
    }

    private void checkRequiredFieldsAreMissing(QuizAnswerSubmitDto quizAnswerSubmitDto) {
        if( quizAnswerSubmitDto.getUsername().isEmpty() || quizAnswerSubmitDto.getQuizName().isEmpty()  || null == quizAnswerSubmitDto.getScore()
               || null == quizAnswerSubmitDto.getTimeTakenToSubmit()) {
            throwException(AppConstants.MISSING_REQUIRED_FIELD_ERROR_CODE, AppConstants.MISSING_REQUIRED_FIELD_ERROR_MESSAGE);
        }
    }

    private void throwException(String code, String errorMessage) {
        throw new BusinessException(code, errorMessage , HttpStatus.BAD_REQUEST, null);
    }
}

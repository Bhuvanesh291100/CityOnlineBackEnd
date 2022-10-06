package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.pojo.QuizRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quizUser")
@Builder
public class QuizUserRegistration extends MongoAuditBaseEntity implements Serializable {
    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 20)
    private String username;
    private String quizId;
    private String quizName;
    private String quizDate;
    private boolean registered;

    List<QuizRegistration> registeredQuizList = new ArrayList<>();

    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;
    private String lastName;
    private String parentFirstName;
    private String parentLastName;

    @NotBlank
    private Long mobileNumber;
    private Long parentMobileNumber;
    private String schoolName;
    private String qualification;
    private String address;

    @NotBlank
    private String emailId;
}
package com.city.online.api.model.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditChangeListener.class)
public class AuditBaseEntity {

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Timestamp createdDate;

    @LastModifiedBy
    private String LastModifiedBy;

    @LastModifiedDate
    private Timestamp LastModifiedDate;
}

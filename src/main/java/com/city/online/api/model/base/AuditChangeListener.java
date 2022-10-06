package com.city.online.api.model.base;

import com.city.online.api.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Configurable
public class AuditChangeListener {
    private static final String DEFAULT_ADMIN_USER = "defaultAdminUser";

    @PrePersist
    public void onCreate(AuditBaseEntity auditBaseEntity) {
        if(null == auditBaseEntity.getCreatedDate()) {
            auditBaseEntity.setCreatedDate(DateTimeUtil.getIstTime());
        }
        auditBaseEntity.setLastModifiedDate(DateTimeUtil.getIstTime());
        auditBaseEntity.setCreatedBy(DEFAULT_ADMIN_USER);
        auditBaseEntity.setLastModifiedBy(DEFAULT_ADMIN_USER);

    }

    @PreUpdate
    public void onUpdate(AuditBaseEntity auditBaseEntity) {
        auditBaseEntity.setLastModifiedBy(DEFAULT_ADMIN_USER);
        auditBaseEntity.setLastModifiedDate(DateTimeUtil.getIstTime());
    }
}

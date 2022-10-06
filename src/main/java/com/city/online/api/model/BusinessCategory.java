package com.city.online.api.model;

import com.city.online.api.model.base.MongoAuditBaseEntity;
import com.city.online.api.model.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "businessCategory")
@Builder
public class BusinessCategory extends MongoAuditBaseEntity implements Serializable {
    @Id
    private String id;

    private Category mainCategory;
    private List<Category> subCategoryList = new ArrayList<>();
}

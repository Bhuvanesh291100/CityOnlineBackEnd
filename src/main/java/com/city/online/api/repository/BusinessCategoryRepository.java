package com.city.online.api.repository;

import com.city.online.api.model.BusinessCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCategoryRepository extends MongoRepository<BusinessCategory, String> {
    @Query("{'mainCategory.categoryCode':?0}")
    BusinessCategory getAllSubCategoriesByMainCategory (String mainCategory);

    @Query("{'mainCategory.status':?0}")
    Page<BusinessCategory> findCategoryByStatus(String status, Pageable pageable);
}
